package com.kevin.tool.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.kevin.tool.async.SimpleThreadPool.ThreadPoolEnum;


/**
 *  批量异步执行工具
 *
 * @author lihongmin
 * @date 2019/11/8 23:07
 * @since 2.1.7
 */
@Slf4j
@Repository
public class AsyncRepository implements BeanFactoryAware {

    /**
     *  存放当前线程需要执行的任务
     *
     *
     */
    private static ThreadLocal<Queue<Asyncable>> QUEUE = ThreadLocal.withInitial(() -> new ConcurrentLinkedDeque<Asyncable>());

    /**
     *
     *  执行策略
     *
     *
     *
     */
    private static ThreadLocal<ThreadPoolEnum> POOL_ENUM = new ThreadLocal<>();

    /**
     *  策略后缀名称
     */
    private static final String SUFFIX = "Impl";

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /**
     *  获取执行策略
     * @param poolEnum 线程名称
     * @return 执行的Bean
     */
    public AsyncRun getStrategy(ThreadPoolEnum poolEnum) {
        Object proxy = beanFactory.getBean(poolEnum.taskName + SUFFIX);
        System.out.println(proxy);
        System.out.println(proxy.getClass());
        Object singletonTarget = AopProxyUtils.getSingletonTarget(proxy);
        System.out.println(singletonTarget.getClass());
//        Object proxy1 = ((AopProxy) proxy).getProxy();


        return (AsyncRun)proxy;
        /*AsyncConfiguration configuration = (AsyncConfiguration)beanFactory.getBean(poolEnum.taskName + SUFFIX);
        return configuration;*/
    }

    /**
     *  添加异步任务
     * @param asyncable 异步任务
     * @return 当前对象
     */
    public AsyncRepository addAsyncable(Asyncable asyncable) {
        QUEUE.get().add(asyncable);
        return this;
    }

    /**
     *  执行策略
     * @param poolEnum 任务策略
     * @return 当前对象
     */
    public AsyncRepository strategy(ThreadPoolEnum poolEnum) {
        POOL_ENUM.set(poolEnum);
        return this;
    }

    /**
     *  执行所有队列任务
     *  执行前先通过{@link #addAsyncable(Asyncable)} 添加任务
     *
     * @param poolEnum 执行策略
     * @return 执行结果
     */
    public List invokeAll(ThreadPoolEnum poolEnum) {
        Queue<Asyncable> queue = QUEUE.get();
        queue.element();
        AsyncRun strategy = getStrategy(poolEnum);

        List<Future> listFuture = new ArrayList<>(queue.size());
        while (queue.peek() != null) {
            listFuture.add(strategy.run(queue.poll()));
        }

        List<Object> result = new ArrayList<>(queue.size());
        getAll(listFuture, result);
        removeAll();
        return result;
    }

    public List invokeAll() {
        ThreadPoolEnum threadPoolEnum = POOL_ENUM.get();
        if (threadPoolEnum == null) {
            throw new RuntimeException("执行策略为空！");
        }
        Queue<Asyncable> queue = QUEUE.get();
        queue.element();
        List<Object> result = new ArrayList<>(queue.size());

        AsyncRun strategy = getStrategy(threadPoolEnum);
        List<Future> listFuture = new ArrayList<>(queue.size());
        while (queue.peek() != null) {
            listFuture.add(strategy.run(queue.poll()));
        }

        getAll(listFuture, result);
        removeAll();
        return result;
    }

    /**
     *  对所有进行{@link ThreadLocal#remove()}操作，防止内存泄漏和脏数据
     */
    private void removeAll() {
        QUEUE.remove();
        POOL_ENUM.remove();
    }
    /**
     *  阻塞获取所有的结果
     *  有异常信息往上抛出，让上层处理 或者 让事务和分布式（二阶提交）事务进行回滚
     *
     * @param listFuture Future集合
     * @param result 封装结果集合
     */
    private void getAll(List<Future> listFuture, List<Object> result) {
        try {
            for (Future<Object> future : listFuture) {
                result.add(future.get());
            }
        } catch (InterruptedException e) {
            log.error("InterruptedException :" + e);
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            log.error("ExecutionException :" + e);
            throw new RuntimeException(e);
        }
    }

    /**
     *  执行任务列表
     *
     * @param tasks 任务列表
     * @param poolEnum 策略
     * @param <T> 执行类型
     * @return 获取执行的Future列表
     */
    private  <T> List<Future> getFutureList(Collection<Asyncable<T>> tasks, ThreadPoolEnum poolEnum) {
        AsyncRun strategy = getStrategy(poolEnum);
        List<Future> listFuture = new ArrayList<>(tasks.size());
        for (Asyncable asyncable : tasks) {
            listFuture.add(strategy.run(asyncable));
        }
        return listFuture;
    }

    /**
     *  执行批量任务，最好是同一个{@link Async} 线程池
     *
     * @param tasks 任务列表
     * @param <T> 执行的函数式接口
//     * @param call 执行的策略，解耦调用者和业务
     * @return 执行结果
     */
    public <T> List<Object> invokeAllObj(Collection<Asyncable<T>> tasks, ThreadPoolEnum poolEnum) {
        if (tasks == null || tasks.isEmpty()) {
            return null;
        }
        StopWatch stopWatchTotal = new StopWatch("total");
        stopWatchTotal.start();

        List<Future> listFuture = getFutureList(tasks, poolEnum);
        List<Object> result = new ArrayList<>(tasks.size());
        getAll(listFuture, result);
        stopWatchTotal.stop();
        log.info("总共花费时间为：" + stopWatchTotal.shortSummary());
        return result;
    }

    /**
     *  执行批量任务，最好是同一个{@link Async} 线程池
     *
     * @param tasks 任务列表
     * @param <T> 执行的函数式接口
//     * @param call 执行的策略，解耦调用者和业务
     * @return 迭代器
     */
    public <T> Iterator<Object> invokeAllIterator(Collection<Asyncable<T>> tasks, ThreadPoolEnum poolEnum) {
        return invokeAllObj(tasks, poolEnum).iterator();
    }

    /**
     *  执行批量任务，最好是同一个{@link Async} 线程池
     *
     * @param tasks 任务列表
     * @param <T> 执行的函数式接口
//     * @param call 执行的策略，解耦调用者和业务
     * @return 数据列表
     */
    public <T> List<T> invokeAllType(Collection<Asyncable<T>> tasks, ThreadPoolEnum poolEnum) {
        if (tasks == null || tasks.isEmpty()) {
            return null;
        }
        AsyncRun strategy = getStrategy(poolEnum);
        StopWatch stopWatchTotal = new StopWatch("total");
        stopWatchTotal.start();
        List<Future> listFuture = new ArrayList<>(tasks.size());
        for (Asyncable asyncable : tasks) {
            listFuture.add(strategy.run(asyncable));
        }

        List result = new ArrayList<>(tasks.size());
        getAll(listFuture, result);
        stopWatchTotal.stop();
        log.info("总共花费时间为：" + stopWatchTotal.shortSummary());
        return result;
    }

    /**
     *  执行批量任务，最好是同一个{@link Async} 线程池
     *
     * @param tasks 任务列表
     * @param <T> 执行的函数式接口
//     * @param call 执行的策略，解耦调用者和业务
     * @return 数据列表
     */
    public <T> Iterator<T> invokeAllTypeIterator(Collection<Asyncable<T>> tasks, ThreadPoolEnum poolEnum) {
        return invokeAllType(tasks, poolEnum).iterator();
    }

}
