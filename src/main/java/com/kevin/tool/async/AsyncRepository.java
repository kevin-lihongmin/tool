package com.kevin.tool.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import java.util.*;
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

    private static ThreadLocal<StopWatch> threadLocal = ThreadLocal.withInitial(() -> new StopWatch());

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
    public AsyncConfiguration getStrategy(ThreadPoolEnum poolEnum) {
        AsyncConfiguration bean = (AsyncConfiguration)beanFactory.getBean(poolEnum.taskName + SUFFIX);
        return bean;
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
        List<Future<Object>> listFuture = new ArrayList<>(tasks.size());
        for (Asyncable asyncable : tasks) {
            listFuture.add(getStrategy(poolEnum).run(asyncable));
        }

        List<Object> result = new ArrayList<>(tasks.size());
        try {
            for (Future<Object> future : listFuture) {
                result.add(future.get());
            }
        } catch (InterruptedException e) {
            log.error("InterruptedException :" + e);
        } catch (ExecutionException e) {
            log.error("ExecutionException :" + e);
        }
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
        StopWatch stopWatchTotal = new StopWatch("total");
        stopWatchTotal.start();
        List<Future<T>> listFuture = new ArrayList<>(tasks.size());
        for (Asyncable asyncable : tasks) {
            listFuture.add(getStrategy(poolEnum).run(asyncable));
        }

        List<T> result = new ArrayList<>(tasks.size());
        try {
            int i = 1;
            for (Future<T> future : listFuture) {
                result.add(future.get());
                log.info("added:" + i);
                i++;
            }
        } catch (InterruptedException e) {
            log.error("InterruptedException :" + e);
        } catch (ExecutionException e) {
            log.error("ExecutionException :" + e);
        }
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
