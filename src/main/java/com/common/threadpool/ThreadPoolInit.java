package com.common.threadpool;

import com.common.threadpool.io.TaskQueue;
import com.common.threadpool.io.TaskThreadFactory;
import com.common.threadpool.io.ThreadPoolExecutorImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  线程池定义和初始化和订单打印线程状态数据
 *
 * @author kevin
 * @date 2020/8/23 14:23
 * @since 1.0.0
 */
@Slf4j
public class ThreadPoolInit implements EnvironmentAware {

    /**
     * 定时执行任务的单线程， 所有任务公用
     * 当前使用的有： 打印线程信息，装运点等定时刷新任务
     */
    public static final ScheduledExecutorService SINGLE_POOL = Executors.newSingleThreadScheduledExecutor();

    /**
     *  线程池容器
     */
    public static final Map<String, ThreadPoolExecutor> THREAD_POOL_EXECUTOR_MAP = new ConcurrentHashMap<>(16);

    /**
     * 是否打印
     */
    private static Boolean printThreadPoolInfoInterval = Boolean.TRUE;

    static {
        // 初始化线程池
        ServiceLoader<ThreadPool> load = ServiceLoader.load(ThreadPool.class);
        load.forEach(threadPool -> threadPool.appendThreadPool().forEach(SimpleThreadPool::putThreadPool));

    }

    @Override
    public void setEnvironment(Environment environment) {
        String[] activeProfiles = environment.getActiveProfiles();
        String pro = "dev";
        for (String activeProfile : activeProfiles) {
            if (pro.equals(activeProfile)) {
                return;
            }
        }
        // 初始化线程池状态信息订单打印
        if (printThreadPoolInfoInterval) {
            printScheduledThreadStats();
        }
    }

    /**
     * 线程池类型， 只是作为标识当前任务是CPU型还是IO型为主
     */
    @SuppressWarnings("unused")
    public enum PoolModel {
        /** io型 */
        IO,
        /** CPU型 */
        CPU,
        /** io型，Tomcat扩展的 juc线程池 */
        FAST_IO

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
     * 启动打印线程
     */
    private static void printScheduledThreadStats() {
        SINGLE_POOL.scheduleAtFixedRate(() -> THREAD_POOL_EXECUTOR_MAP.forEach((name, threadPool) -> {
            log.info("{} Pool Size: {}", name, threadPool.getPoolSize());
            log.info("{} Active Threads: {}", name, threadPool.getActiveCount());
            log.info("{} Number of Tasks Completed: {}", name, threadPool.getCompletedTaskCount());
            log.info("{} Number of Tasks in Queue: {}", name, threadPool.getQueue().size());
        }), 0, 5, TimeUnit.SECONDS);
    }

    /**
     * 往线程池容器中放入线程池池
     * @param threadPoolEntity 线程池定义对象
     */
    public static void putThreadPool(ThreadPoolEntity threadPoolEntity) {
        ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor(threadPoolEntity);
        log.info("name: {}, threadPoolExecutor = {}", threadPoolEntity.taskName, threadPoolExecutor);
        THREAD_POOL_EXECUTOR_MAP.put(threadPoolEntity.taskName, threadPoolExecutor);
    }

    /**
     *  根据枚举获取线程池
     * @param entity 线程池枚举
     */
    private static ThreadPoolExecutor getThreadPoolExecutor(ThreadPoolEntity entity) {
        ThreadPoolExecutor executor;
        if (entity.poolModel == PoolModel.FAST_IO) {
            if (!(entity.blockingQueue instanceof TaskQueue)) {
                throw new RuntimeException("PoolModel.FAST_IO 类型的线程池，只能创建 " + TaskQueue.class.getName() + " 类型的队列！");
            }
            TaskQueue taskQueue = (TaskQueue)entity.blockingQueue;
            ThreadPoolExecutorImpl threadPoolExecutor = new ThreadPoolExecutorImpl(entity.corePoolNum, entity.maxPoolNum, entity.deleteThreadNum,
                    entity.deleteTreadUnit,  taskQueue, new TaskThreadFactory(entity.taskName), entity.rejectedExecutionHandler);
            // 设置父类，用于判断线程的对列表是否真的满了
            taskQueue.setParent(threadPoolExecutor);
            executor = threadPoolExecutor;

            log.info("init ThreadPoolExecutorImpl and TaskQueue！");
        } else {
            executor = new ThreadPoolExecutor(entity.corePoolNum, entity.maxPoolNum, entity.deleteThreadNum, entity.deleteTreadUnit,
                    entity.blockingQueue, new DefaultThreadFactory(entity.taskName), entity.rejectedExecutionHandler);
            // 是否预热核心线程
            if (entity.preStartAllCoreThreads) {
                executor.prestartAllCoreThreads();
            }

            log.info("init ThreadPoolExecutor and {}}！", entity.blockingQueue.getClass().getSimpleName());
        }

        try {
            // 是否允许核心线程超时
            if (entity.allowsCoreThreadTimeOut) {
                executor.allowsCoreThreadTimeOut();
            }
        } catch (NullPointerException e) {
            log.error("初始化线程池错误:" + e);
        }
        return executor;
    }

}
