package com.common.threadpool;

import java.util.List;

/**
 *  线程池定义
 * @author kevin
 * @date 2020/8/23 13:43
 * @since 1.0.0
 */
@FunctionalInterface
public interface ThreadPool {

    /**
     * 添加线程池
     * @return 线程池定义对象
     */
    List<ThreadPoolEntity> appendThreadPool();

}
