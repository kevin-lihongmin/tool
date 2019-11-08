package com.kevin.tool.async;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 线程池工具类
 * @author lihongmin
 * @date 2019/10/28 12:46
 * @since 2.1.7
 */
@Slf4j
public class SimpleThreadPool {

    /**
     *  线程池工具map
     */
    public static final Map<ThreadPoolEnum, ThreadPoolExecutor> THREAD_POOL_EXECUTOR_MAP = new HashMap<ThreadPoolEnum, ThreadPoolExecutor>(16);

    /**
     *  线程池集合枚举
     */
    public enum ThreadPoolEnum {
        MANAGE("manage", "统计订单相关线程池", 5, 8, 30, TimeUnit.SECONDS,
                new LinkedBlockingDeque(50), new ThreadPoolExecutor.AbortPolicy()),
        CREATE_ORDER("createOrder", "创建订单相关线程池", 5, 20, 60, TimeUnit.SECONDS,
                new LinkedBlockingDeque(50), new ThreadPoolExecutor.CallerRunsPolicy());

        ThreadPoolEnum(String taskName, String detail, int corePoolNum, int maxPoolNum, int deleteThreadNum, TimeUnit deleteTreadUnit, LinkedBlockingDeque blockingDeque, RejectedExecutionHandler rejectedExecutionHandler) {
            this.taskName = taskName;
            this.detail = detail;
            this.corePoolNum = corePoolNum;
            this.maxPoolNum = maxPoolNum;
            this.deleteThreadNum = deleteThreadNum;
            this.deleteTreadUnit = deleteTreadUnit;
            this.blockingDeque = blockingDeque;
            this.rejectedExecutionHandler = rejectedExecutionHandler;
        }

        /**
         *  先池名称
         */
        public String taskName;

        /**
         *  线程池说明
         */
        private String detail;

        private int corePoolNum;

        private int maxPoolNum;

        private int deleteThreadNum;

        private TimeUnit deleteTreadUnit;

        private LinkedBlockingDeque blockingDeque;

        private RejectedExecutionHandler rejectedExecutionHandler;

        static Map<String, ThreadPoolEnum> map = new HashMap<String, ThreadPoolEnum>(16);
        static {
            for (ThreadPoolEnum threadPoolName : ThreadPoolEnum.values()) {
                map.put(threadPoolName.getTaskName(), threadPoolName);
            }
        }

        /**
         *  根据线程池名称获取线程池枚举
         * @param taskName 线程池名称
         * @return 线程池枚举
         */
        public ThreadPoolEnum getThreadPoolEnum(String taskName) {
            return map.get(taskName);
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

    }

    /**
     *  线程工厂
     */
    static class DefaultThreadFactory implements ThreadFactory {

        /**
         *  定义线程组
         */
        static ThreadGroup threadGroup;

        /**
         *  定义每个线程池中每个线程的名称后缀数字
         */
        static final AtomicInteger THREAD_NUMBER = new AtomicInteger(1);

        /**
         *  定义每个线程词的名称前缀
         */
        static String namePrefix;

        public DefaultThreadFactory(String name) {
            final SecurityManager securityManager = System.getSecurityManager();
            threadGroup = (securityManager != null) ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = name + "-thread-";
        }

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(threadGroup, runnable, namePrefix + THREAD_NUMBER.getAndIncrement(), 0);
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            if(thread.getPriority() != Thread.NORM_PRIORITY){
                thread.setPriority(Thread.NORM_PRIORITY);
            }
            return thread;
        }
    }

    /**
     *  初始化线程池
     */
    static {
        for (ThreadPoolEnum threadPoolEnum : ThreadPoolEnum.values()) {
            THREAD_POOL_EXECUTOR_MAP.put(threadPoolEnum, getThreadPoolExecutor(threadPoolEnum));
        }
    }

    /**
     *  根据枚举获取线程池
     * @param threadPoolEnum 线程池枚举
     */
    private static ThreadPoolExecutor getThreadPoolExecutor(ThreadPoolEnum threadPoolEnum) {
        return new ThreadPoolExecutor(threadPoolEnum.corePoolNum,
                threadPoolEnum.maxPoolNum,
                threadPoolEnum.deleteThreadNum,
                threadPoolEnum.deleteTreadUnit,
                threadPoolEnum.blockingDeque,
                new DefaultThreadFactory(threadPoolEnum.taskName),
                threadPoolEnum.rejectedExecutionHandler);
    }

    /**
     *  执行没有返回值的任务
     * @param threadPoolEnum 线程池枚举
     * @param runnable 任务
     */
    public static void execute(ThreadPoolEnum threadPoolEnum, Runnable runnable) {
        if (!THREAD_POOL_EXECUTOR_MAP.containsKey(threadPoolEnum)) {
            throw new IllegalArgumentException("未找到线程池：" + threadPoolEnum.taskName);
        }
        // 执行任务
        THREAD_POOL_EXECUTOR_MAP.get(threadPoolEnum).execute(runnable);
    }

    /**
     * 执行有返回值任务
     *
     * @param threadPoolEnum
     * @param callable
     * @param <T>
     * @return
     */
    public static <T> List<Future<T>> execute(ThreadPoolEnum threadPoolEnum, Callable<T>... callable) {
        if (!THREAD_POOL_EXECUTOR_MAP.containsKey(threadPoolEnum)) {
            throw new IllegalArgumentException("未配置线程池" + threadPoolEnum.taskName);
        }
        ThreadPoolExecutor executor = THREAD_POOL_EXECUTOR_MAP.get(threadPoolEnum);
        List<Future<T>> futureList = new ArrayList<Future<T>>(callable.length);
        for (int i = 0; i < callable.length; i++) {
            Future<T> f = executor.submit(callable[i]);
            futureList.add(f);
        }
        return futureList;
    }

    /**
     * 批量执行并行任务
     * @param threadPoolEnum
     * @param callableList
     * @return
     */
    public static <T> List<Future<T>> executeAll(ThreadPoolEnum threadPoolEnum, List<Callable<T>> callableList) {
        if (!THREAD_POOL_EXECUTOR_MAP.containsKey(threadPoolEnum)) {
            throw new IllegalArgumentException("未配置线程池" + threadPoolEnum.taskName);
        }
        try {
            return THREAD_POOL_EXECUTOR_MAP.get(threadPoolEnum).invokeAll(callableList, 2000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.error("线程池批量执行任务异常失败", e);
        } catch (Exception e) {
            log.error("线程池批量执行任务异常失败", e);
        }
        return new ArrayList<>();
    }

    /**
     * 执行无返回的线程任务
     * @param threadPoolEnum
     * @param r
     */
    public static void executeRunnable(ThreadPoolEnum threadPoolEnum, Runnable... r) {
        if (!THREAD_POOL_EXECUTOR_MAP.containsKey(threadPoolEnum)) {
            throw new IllegalArgumentException("未配置线程池" + threadPoolEnum.taskName);
        }
        ThreadPoolExecutor executor = THREAD_POOL_EXECUTOR_MAP.get(threadPoolEnum);
        for (int i = 0; i < r.length; i++) {
            executor.submit(r[i]);
        }
    }

}