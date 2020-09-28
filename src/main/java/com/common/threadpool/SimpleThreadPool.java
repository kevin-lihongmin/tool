package com.common.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 线程池工具类
 * @author kevin
 * @date 2019/10/28 12:46
 * @since 2.1.7
 */
@Slf4j
@Component
@SuppressWarnings("ALL")
public class SimpleThreadPool extends ThreadPoolInit implements EnvironmentAware {

    /**
     * 默认最大的超时时间
     */
    private static int DEFAULT_TIMEOUT = 50000;

    @Override
    public void setEnvironment(Environment environment) {
        // 执行父类中的环境设置
        super.setEnvironment(environment);

        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles == null) {
            return;
        }
        String pro = "prod";
        for (String activeProfile : activeProfiles) {
            if (pro.equals(activeProfile)) {
                if (log.isInfoEnabled()) {
                    log.info("active spring profile prod");
                }
                DEFAULT_TIMEOUT = 2000;
            }
        }
        if (log.isInfoEnabled()) {
            log.info("SimpleThreadPool DEFAULT_TIMEOUT = {} ms", DEFAULT_TIMEOUT);
        }
    }

    /**
     *  执行没有返回值的任务
     * @param key 线程池枚举
     * @param runnable 任务
     */
    public static void execute(String key, Runnable runnable) {
        if (!THREAD_POOL_EXECUTOR_MAP.containsKey(key)) {
            throw new IllegalArgumentException("未找到线程池：" + key);
        }
        // 执行任务
        THREAD_POOL_EXECUTOR_MAP.get(key).execute(runnable);
    }

    /**
     * 执行有返回值任务
     *
     * @param key 线程池名称
     * @param callable 需要执行的任务可变数组
     * @param <T> 任务类型
     * @return 任务结果
     */
    public static <T> List<Future<T>> execute(String key, Callable<T>... callable) {
        if (!THREAD_POOL_EXECUTOR_MAP.containsKey(key)) {
            throw new IllegalArgumentException("未配置线程池" + key);
        }
        ThreadPoolExecutor executor = THREAD_POOL_EXECUTOR_MAP.get(key);
        List<Future<T>> futureList = new ArrayList<Future<T>>(callable.length);
        for (int i = 0; i < callable.length; i++) {
            Future<T> f = executor.submit(callable[i]);
            futureList.add(f);
        }
        return futureList;
    }

    /**
     * 批量执行并行任务, 使用默认的最大超时时间，单位毫秒
     * @param key 线程池名称
     * @param callableList 任务列表
     * @return 结果
     */
    public static <T> List<Future<T>> executeAll(String key, List<Callable<T>> callableList) {
        return executeAll(key, DEFAULT_TIMEOUT, callableList);
    }

    /**
     * 批量执行并行任务
     * @param key 线程池名称
     * @param maxTimeout 最大超时时间，没有取默认值，单位2000毫秒
     * @param callableList 任务列表
     * @return 结果
     */
    public static <T> List<Future<T>> executeAll(String key, int maxTimeout, List<Callable<T>> callableList) {
        if (!THREAD_POOL_EXECUTOR_MAP.containsKey(key)) {
            throw new IllegalArgumentException("未配置线程池" + key);
        }
        try {
            return THREAD_POOL_EXECUTOR_MAP.get(key).invokeAll(callableList, maxTimeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.error("线程池批量执行任务异常失败", e);
        } catch (Exception e) {
            log.error("线程池批量执行任务异常失败", e);
        }
        return new ArrayList<>();
    }

    /**
     * @param key 线程池名称
     * @param callableList 任务列表
     * @return 结果
     */
    public static void executeRunnable(String key, Runnable... r) {
        if (!THREAD_POOL_EXECUTOR_MAP.containsKey(key)) {
            throw new IllegalArgumentException("未配置线程池" + key);
        }
        ThreadPoolExecutor executor = THREAD_POOL_EXECUTOR_MAP.get(key);
        for (int i = 0; i < r.length; i++) {
            executor.submit(r[i]);
        }
    }

}