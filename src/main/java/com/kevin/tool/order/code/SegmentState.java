package com.kevin.tool.order.code;

/**
 *  对外流程状态（节点）
 *
 * @author lihongmin
 * @date 2020/7/10 11:03
 * @since 1.0.0
 */
public enum SegmentState {

    /**
     *  审核采购订单
     */
    PURCHASE_AUDIT,
    /**
     *  创建销售订单
     */
    SALE_CREATE,
    /**
     *  预销售审核
     */
    PRE_SELL_AUDIT,
    /**
     *  审核销售订单
     */
    SALE_AUDIT,
    /**
     *  运装条件
     */
    SHIPPING_CONDITION

}
