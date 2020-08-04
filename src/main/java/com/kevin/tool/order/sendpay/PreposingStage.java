package com.kevin.tool.order.sendpay;

/**
 *  对外流程状态（节点）， 没有订单码【订单码生成的前置检查】
 *
 * @author lihongmin
 * @date 2020/7/10 11:03
 * @since 1.0.0
 */
public enum PreposingStage {

    /**
     *  审核采购订单
     */
    PURCHASE_CREATE,
    /**
     *  没有采购单，直接销售开单
     */
    SALE_CREATE

}
