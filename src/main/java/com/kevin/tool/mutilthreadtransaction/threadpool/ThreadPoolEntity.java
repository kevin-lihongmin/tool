package com.kevin.tool.mutilthreadtransaction.threadpool;

import lombok.AllArgsConstructor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.TimeUnit;

/**
 *  线程池定义类
 * @author kevin
 * @date 2020/8/23 13:53
 * @since 1.0.0
 */
@AllArgsConstructor
public final class ThreadPoolEntity {

    /**
     *  先池名称
     */
    public final String taskName;

    /**
     * 线程池的最大超时时间
     */
//    public final int maxTimeout = 2000;

    /**
     * 线程池任务类型
     */
    public final SimpleThreadPool.PoolModel poolModel;

    /**
     * 是否允许核心线程超时
     */
    public final Boolean allowsCoreThreadTimeOut;

    /**
     * 是否预热核心线程池
     */
    public final Boolean preStartAllCoreThreads;

    /**
     *  线程池说明
     */
    public final String detail;

    /**
     * 核心线程数
     */
    public final int corePoolNum;

    /**
     * 最大线程数
     */
    public final int maxPoolNum;

    /**
     * 超时时间
     */
    public final int deleteThreadNum;

    /**
     * 超时单位
     */
    public final TimeUnit deleteTreadUnit;

    /**
     * 任务队列，没有特殊理由不能使用无界队列
     */
    public final BlockingQueue<Runnable> blockingQueue;

    /**
     * 拒绝策略
     */
    public final RejectedExecutionHandler rejectedExecutionHandler;

}