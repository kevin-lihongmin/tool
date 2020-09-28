package com.biz.check.config;

import com.biz.check.constant.ThreadPoolEnum;
import com.common.threadpool.SimpleThreadPool;
import com.common.threadpool.ThreadPool;
import com.common.threadpool.ThreadPoolEntity;
import com.common.threadpool.io.TaskQueue;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *  线程池初始化， Java SPI实现
 *
 * @author kevin
 * @date 2020/8/23 14:36
 * @since 1.0.0
 */
public class ThreadPoolImpl implements ThreadPool {

    @Override
    public List<ThreadPoolEntity> appendThreadPool() {
        return Lists.newArrayList(
                new ThreadPoolEntity(ThreadPoolEnum.CHECK_CHAIN_LIST.name(), SimpleThreadPool.PoolModel.FAST_IO,  Boolean.FALSE, Boolean.TRUE,
                        "检查项线程池【Per PO Per Thread】", 5, 8, 30, TimeUnit.SECONDS,
                        new TaskQueue(50), new ThreadPoolExecutor.AbortPolicy()),

                new ThreadPoolEntity(ThreadPoolEnum.CREDIT_TYPE_ITEM.name(), SimpleThreadPool.PoolModel.FAST_IO, Boolean.FALSE, Boolean.TRUE,
                        "信贷检查项线程池", 4, 8, 60, TimeUnit.SECONDS,
                        new TaskQueue(15), new ThreadPoolExecutor.CallerRunsPolicy())/*,

                new ThreadPoolEntity(ProductStructService.PRODUCT_STRUCT_THREAD_POOL, SimpleThreadPool.PoolModel.FAST_IO, Boolean.FALSE, Boolean.TRUE,
                        "组装产品基层封装线程池", 4, 8, 60, TimeUnit.SECONDS,
                        new TaskQueue(15), new ThreadPoolExecutor.CallerRunsPolicy())*/

            );
    }
}
