package com.quanyou.qup.biz.check.feign.dto.enums;

/**
 *  可用量 查询类型
 * @author kevin
 * @date 2020/8/18 15:34
 * @since 1.0.0
 */
public enum AtpSelectByEnum {

    /**
     * 可用量检查：SAP可用量（Atp） - VSO占用量 -货源调配占用量 - 当前订单需求量
     * 使用：货源安排、单独开销售订单
     */
    ATP_SO,

    /**
     * 可用量检查：SAP可用量（Atp） -货源调配占用量 - 当前订单需求量
     * 使用：VSO转SO、独资办开单
     */
    ATP_NO_VSO

}
