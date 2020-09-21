package com.quanyou.qup.biz.check.constant;

/**
 *  线程池名称枚举
 * @author kevin
 * @date 2020/8/23 14:38
 * @since 1.0.0
 */
public enum ThreadPoolEnum {

    /**
     * 检查服务链，每个检查项 一个线程
     */
    CHECK_CHAIN_LIST,

    /**
     * 信贷检查项线程池
     */
    CREDIT_TYPE_ITEM
}
