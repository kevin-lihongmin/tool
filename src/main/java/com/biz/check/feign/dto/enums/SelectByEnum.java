package com.biz.check.feign.dto.enums;

/**
 *  查询类型的枚举
 *
 * @author kevin
 * @date 2020/8/11 9:03
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public enum SelectByEnum {

    /**
     * 使用[主键]查询
     */
    ID,

    /**
     * 使用[编码]查询
     */
    CODE,

    /* ------------------------- 信贷：业务账号级别 ----------------------------- */

    /**
     *  信贷检查： sap信贷 - 当前订单需求金额
     *  使用：报表, VSO转SO的开单
     */
    CREDIT_SIMPLE,

    /**
     * 信贷检查： sap信贷 - VSO占用金额 - 当前订单需求金额
     * 使用： 货源安排、直接开销售订单
     */
    CREDIT_VSO_FROZEN,

    /**
     * 信贷检查： sap信贷 - PO占用金额[已提交并且未完成] - VSO占用量金额 - 当前订单需求金额
     * 使用： 已提交类型的PO开单（未提交类型的不查询）
     */
    CREDIT_PO_VSO_FROZEN,

    /* ------------------------- 可用量：包件级别 ----------------------------- */

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
