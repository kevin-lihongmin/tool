package com.kevin.tool.timeconsume;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.StopWatch;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
public class AsyncUtil {

    /**
     *  执行批量任务，最好是同一个{@link Async} 线程池
     *
     * @param tasks 任务列表
     * @param <T> 执行的函数式接口
     * @param call 执行的策略，解耦调用者和业务
     * @return 执行结果
     */
    public static <T> LinkedHashSet<Object> invokeAllObj(Collection<Asyncable<T>> tasks, AsyncConfiguration.AsyncCall call) {
        if (tasks == null || tasks.isEmpty()) {
            return null;
        }
        StopWatch stopWatchTotal = new StopWatch("total");
        stopWatchTotal.start();
        List<Future<Object>> listFuture = new ArrayList<>(tasks.size());
        for (Asyncable asyncable : tasks) {
            listFuture.add(call.run(asyncable));
        }

        LinkedHashSet<Object> result = new LinkedHashSet<>(tasks.size());
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
     * @param call 执行的策略，解耦调用者和业务
     * @return 迭代器
     */
    public static <T> Iterator<Object> invokeAllIterator(Collection<Asyncable<T>> tasks, AsyncConfiguration.AsyncCall call) {
        return invokeAllObj(tasks, call).iterator();
    }

    /**
     *  执行批量任务，最好是同一个{@link Async} 线程池
     *
     * @param tasks 任务列表
     * @param <T> 执行的函数式接口
     * @param call 执行的策略，解耦调用者和业务
     * @return 数据列表
     */
    public static <T> LinkedHashSet<T> invokeAllType(Collection<Asyncable<T>> tasks, AsyncConfiguration.AsyncCall call) {
        if (tasks == null || tasks.isEmpty()) {
            return null;
        }
        StopWatch stopWatchTotal = new StopWatch("total");
        List<Future<T>> listFuture = new ArrayList<>(tasks.size());
        for (Asyncable asyncable : tasks) {
            listFuture.add(call.run(asyncable));
        }

        LinkedHashSet<T> result = new LinkedHashSet<>(tasks.size());
        try {
            for (Future<T> future : listFuture) {
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
     * @param call 执行的策略，解耦调用者和业务
     * @return 数据列表
     */
    public static <T> Iterator<T> invokeAllTypeIterator(Collection<Asyncable<T>> tasks, AsyncConfiguration.AsyncCall call) {
        return invokeAllType(tasks, call).iterator();
    }

}
