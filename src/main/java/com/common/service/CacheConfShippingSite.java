package com.common.service;

import com.quanyou.qup.middle.common.threadpool.SimpleThreadPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 缓存全量（ConfigShipSite）数据
 *
 * @author kevin
 * @date 2020/9/1 10:25
 * @since 1.0.0
 */
@Slf4j
public class CacheConfShippingSite implements ApplicationRunner {
    /**
     * 存放缓存对象
     */
    private static final ConcurrentSkipListMap<String, Object> CACHE = new ConcurrentSkipListMap<>();

    private static final ReadWriteLock READ_WRITE_LOCK = new ReentrantReadWriteLock();
    private final Lock readLock = READ_WRITE_LOCK.readLock();
    private final Lock writeLock = READ_WRITE_LOCK.writeLock();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        SimpleThreadPool.SINGLE_POOL.scheduleAtFixedRate(
                this::refreshCacheInterval, 0, 10 * 60, TimeUnit.SECONDS
        );
    }

    /**
     * 十分钟一次
     */
    public void write(String key, Object value) {
        writeLock.lock();
        try {
            CACHE.put(key, value);
        } finally {
            writeLock.unlock();
        }

    }

    /**
     * @param key 缓存key
     * @return Object
     */
    public Object read(String key) {
        readLock.lock();
        try {
            return CACHE.get(key);
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 业务线程
     */
    public void refreshCacheInterval() {
    }

}
