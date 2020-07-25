package com.kevin.tool.order.code.generate;

import com.kevin.tool.order.code.generate.param.CodeParam;

/**
 *  订单码工厂
 * @author lihongmin
 * @date 2020/7/2 17:26
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public interface CodeFactory {

    /**
     *  订单类型
     * @author lihongmin
     * @date 2020/7/1 9:38
     * @since 1.0.0
     */
    enum OrderType {
        /**
         *  采购订单
         */
        PURCHASE_ORDER,
        /**
         *  销售订单
         */
        SALE_ORDER
    }

    /**
     *  订单码工厂，根据订单类型和参数 -> 生成订单编码
     *
     * @param codeParam 订单参数
     * @return 订单编码
     */
    String generateCode(CodeParam codeParam);

    /**
     *  订单码工厂，根据订单类型和参数 -> 生成订单编码
     *
     * @param codeParam 订单参数
     * @param orderType 订单类型
     * @return 订单编码
     */
    String generateCode(CodeParam codeParam, DefaultCodeFactory.OrderType orderType);

}
