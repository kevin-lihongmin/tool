package com.common.lock.distributed;

import com.quanyou.qup.core.util.BeanTools;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁Lock接口实现类
 *
 * @author: zhouwei6
 * @since: 2020/09/02
 */
@Slf4j
@SuppressWarnings("unused")
@ConditionalOnClass()
@ConditionalOnBean(RedissonClient.class)
public class RedissonDistributedLocker implements DistributedLocker {


    private static volatile RedissonClient redissonClient;

    public static RedissonClient getRedissonClient() {
        if (redissonClient == null) {
            synchronized (RedissonDistributedLocker.class) {
                if (redissonClient == null) {
                    redissonClient = BeanTools.getBean(RedissonClient.class);
                }
            }
        }
        return redissonClient;
    }


    @Override
    public RLock lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }

    @Override
    public RLock lock(String lockKey, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(leaseTime, TimeUnit.SECONDS);
        return lock;
    }

    @Override
    public RLock lock(String lockKey, TimeUnit unit, int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;
    }

    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }

    @Override
    public void unlock(RLock lock) {
        lock.unlock();
    }

}
