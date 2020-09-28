package com.common.lock.distributed;

import com.quanyou.qup.core.util.BeanTools;
import com.quanyou.qup.core.util.StringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 *  分布式锁（设置超时时间，就一直进行获取）
 *  当前使用： vso删除（归还预售计划量） 、货源安排转vso（查询预售和减预售的时间片）
 *
 *  <p>
 *  1、可以在{@link CacheConfig} 中添加自己的枚举信息，调用：
 *      {@link DistributedLockUtil#invokeInLock(CallBack, CacheConfig, String...)} 或者 {@link DistributedLockUtil#getInstance()#invokeInLock(CallBack, String...)}
 *
 *  2、也可以不使用枚举：
 *  {@link DistributedLockUtil#getInstance()#setPrefix(String)#invokeInLock(CallBack, String...)} 调用前可以设置超时 {@link #setTimeout(long, long)}
 *
 * @author kevin
 * @date 2020/9/1 20:05
 * @since 1.0.0
 */
@SuppressWarnings("unused")
@Slf4j
public class DistributedLockUtil {

    /**
     * redisson 客户端
     */
    private static volatile RedissonClient redissonClient;

    /**
     * 前缀参数
     */
    private static final ThreadLocal<Parameter> PREFIX_PARAMETER = ThreadLocal.withInitial(() -> null);

    private DistributedLockUtil() {
    }

    private static class Singleton {
        private static final DistributedLockUtil INSTANCE = new DistributedLockUtil();
    }
    public static DistributedLockUtil getInstance() {
        return Singleton.INSTANCE;
    }
    
    /**
     * 获取 redisson 客户端
     * @return redisson
     */
    private static RedissonClient getRedissonClient() {
        if (redissonClient == null) {
            synchronized (DistributedLockUtil.class) {
                if (redissonClient == null) {
                     redissonClient = BeanTools.getBean(RedissonClient.class);
                }
            }
        }
        return redissonClient;
    }

    /**
     * 添加前缀信息
     * @param prefix 前缀
     * @return 当前单利对象
     */
    public DistributedLockUtil setPrefix(String prefix) {
        Parameter parameter = PREFIX_PARAMETER.get();
        if (parameter == null) {
            PREFIX_PARAMETER.set(new Parameter(prefix));
            return this;
        }
        parameter.setPrefix(prefix);
        return this;
    }

    /**
     * 设置获取的超时时间
     * @param waitTime 等待时间
     * @param leaseTime 续命（租赁）时间
     * @return 当前单利对象
     */
    public DistributedLockUtil setTimeout(long waitTime, long leaseTime) {
        Parameter parameter = PREFIX_PARAMETER.get();
        if (parameter == null) {
            PREFIX_PARAMETER.set(new Parameter(waitTime, leaseTime));
            return this;
        }
        parameter.setWaitTime(waitTime);
        parameter.setLeaseTime(leaseTime);
        return this;
    }

    /**
     * 设置获取的超时时间
     * @param cacheConfig 缓存配置
     * @return 当前单利对象
     */
    public DistributedLockUtil setCacheConfig(CacheConfig cacheConfig) {
        Parameter parameter = PREFIX_PARAMETER.get();
        if (parameter == null) {
            PREFIX_PARAMETER.set(new Parameter(cacheConfig));
            return this;
        }
        parameter.setCacheConfig(cacheConfig);
        return this;
    }

    /**
     * 使用单利对象调用该方法 在排序的分布式锁中，执行操作
     *
     *  调用前需要调用{@link #setPrefix(String)} 或者 {@link #setCacheConfig(CacheConfig)} 也可以调用{@link #setTimeout(long, long)}
     *
     * @param method 需要回调执行的方法体
     * @param lockKey 缓存key列表
     * @param <V> 回调返回值
     * @return 回调返回信息
     */
    public <V> V invokeInLock(CallBack<V> method, String... lockKey) {
        Parameter parameter = PREFIX_PARAMETER.get();
        if (parameter == null || (parameter.getCacheConfig() == null && StringUtil.isBlank(parameter.getPrefix()))) {
            throw new RuntimeException("请先调用setPrefix或setCacheConfig方法！");
        }
        try {
            return invokeInLock(method, null, lockKey);
        } finally {
            PREFIX_PARAMETER.remove();
        }
    }

    /**
     * 在排序的分布式锁中，执行操作
     * @param method 需要回调执行的方法体
     * @param cacheConfig 可以为null， 则自己带前置传入
     * @param lockKey 缓存key列表
     * @param <V> 回调返回值
     * @return 回调返回信息
     */
    public static <V> V invokeInLock(CallBack<V> method, @Nullable CacheConfig cacheConfig, String... lockKey) {
        Parameter parameter = PREFIX_PARAMETER.get();
        if (lockKey == null || (cacheConfig == null && parameter == null)) {
            throw new RuntimeException("请求参数不能为空！");
        }
        boolean hasCacheConfig = cacheConfig == null;
        final String prefix = hasCacheConfig ? parameter.getPrefix() : cacheConfig.cachePrefix;
        long waitTime = hasCacheConfig ? parameter.getWaitTime() : cacheConfig.waitTime;
        long leaseTime = hasCacheConfig ? parameter.getLeaseTime() : cacheConfig.leaseTime;

        // 获取批量的分布式锁
        RLock[] rLocks = Arrays.stream(lockKey)
                // 进行排序，防止获取分布式锁时死锁
                .sorted()
                // 组装获取锁
                .map(key -> getRedissonClient().getLock(prefix + key))
                .toArray(RLock[]::new);
        RLock lock = redissonClient.getMultiLock(rLocks);

        // 加锁
        boolean isLock = false;
        try {
            isLock = waitTime == 0 ? lock.tryLock() : lock.tryLock(waitTime, leaseTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.error("获取分布式锁失败！");

        }
        V result = null;
        if (isLock) {
            try {
                // 在加锁的情况下创建vso【查预售量，创建vso，修改剩余预售量】
                result = method.callBack();
            } finally {
                // 最终进行解锁
                try {
                    lock.unlock();
                } catch (Exception e) {
                    log.error("分布式锁超时【续命锁也超时】，已经自动解锁！");
                }
            }
        }
        return result;
    }

    /**
     *  缓存前缀
     * @author kevin
     * @date 2020/9/2 10:04
     * @since 1.0.0
     */
    public enum CacheConfig {

        /** 预销售分布式锁前缀 */
        PRE_SELL("pre_sell_lock_", 5000000, 5000);

        /** 缓存前缀 */
        public String cachePrefix;

        /** 等待时间(毫秒) */
        public long waitTime;

        /** 续命（租赁）时间(毫秒) */
        public long leaseTime;

        CacheConfig(String cachePrefix, long waitTime, long leaseTime) {
            this.cachePrefix = cachePrefix;
            this.waitTime = waitTime;
            this.leaseTime = leaseTime;
        }
    }

    /**
     * 前置配置参数
     */
    @Data
    private static class Parameter {
        /** 前缀 */
        private String prefix;

        /** 缓存枚举 */
        private CacheConfig cacheConfig;

        /** 等待时间(毫秒) */
        private long waitTime;

        /** 续命（租赁）时间 */
        private long leaseTime;

        public Parameter(String prefix) {
            this.prefix = prefix;
        }

        public Parameter(CacheConfig cacheConfig) {
            this.cacheConfig = cacheConfig;
        }

        public Parameter(long waitTime, long leaseTime) {
            this.waitTime = waitTime;
            this.leaseTime = leaseTime;
        }
    }

}
