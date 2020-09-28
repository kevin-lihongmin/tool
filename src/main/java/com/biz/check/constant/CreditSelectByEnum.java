package com.biz.check.constant;

/**
 *  信贷查询类型的枚举
 *
 * @author kevin
 * @date 2020/8/11 9:03
 * @since 1.0.0
 */
public enum CreditSelectByEnum {

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
    CREDIT_PO_VSO_FROZEN

}
