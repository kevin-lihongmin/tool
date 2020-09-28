package com.biz.check.manager.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Repository;

/**
 *  缓存信贷范围数据
 * @author kevin
 * @date 2020/9/3 10:10
 * @since 1.0.0
 */
@Slf4j
@Repository
public class ConfigServiceCache /*implements InitializingBean, CommandLineRunner */{

    /**
     * 缓存全量的 {@code CONF_CREDIT_SCOPE}数据
     */
    /*private static final Set<RemoteConfCreditScopeDTO> CACHE = new ConcurrentHashSet<>(500);


    private final ConfigServiceFeignService confDelyShippingConditionService;

    public ConfigServiceCache(ConfigServiceFeignService confDelyShippingConditionService) {
        this.confDelyShippingConditionService = confDelyShippingConditionService;
    }

    @Override
    public void run(String... args) {
        List<RemoteConfCreditScopeDTO> allCreditScope = confDelyShippingConditionService.getAllCreditScope();
        log.info("全量信贷范围数据 list= {}", JSON.toJSONString(allCreditScope));
        if (CollectionUtil.isEmpty(allCreditScope)) {
            log.error("获取全量信贷范围数据信息错误");
            throw new RuntimeException("获取全量信贷范围数据信息错误");
        }

        writeAll(allCreditScope);
    }

    *//**
     * 读写锁
     *//*
    private static final ReadWriteLock READ_WRITE_LOCK = new ReentrantReadWriteLock();
    private final Lock readLock = READ_WRITE_LOCK.readLock();
    private final Lock writeLock = READ_WRITE_LOCK.writeLock();

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    *//**
     * 写入操作
     * @param value 单条数据
     *//*
    public void writeAll(List<RemoteConfCreditScopeDTO> value) {
        writeLock.lock();
        try {
            CACHE.addAll(value);
        } finally {
            writeLock.unlock();
        }
    }

    *//**
     *  查询信贷范围
     * @param creditDTO 销售范围
     * @return 信贷范围
     *//*
    public String getCreditScope(CheckRequestCreditDTO creditDTO) {
        readLock.lock();
        try {
            try {
                for (RemoteConfCreditScopeDTO dto : CACHE) {
                    if (dto.getSaleOrgCode().equals(creditDTO.getSaleOrgCode()) && dto.getSaleChannelCode().equals(creditDTO.getSaleChannelCode())
                        && dto.getProductGroupCode().equals(creditDTO.getProductGroupCode())) {
                        return dto.getCreditRange();
                    }
                }
            } catch (Exception e) {
                return null;
            }
            return null;
        } finally {
            readLock.unlock();
        }
    }*/

}
