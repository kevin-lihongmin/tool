package com.kevin.tool.order.sendpay.check.marker;

import com.kevin.tool.order.sendpay.SaleCreateDTO;

/**
 *  标识服务
 *
 * @author lihongmin
 * @date 2020/7/10 16:07
 * @since 1.0.0
 */
public interface MarkerFlagService {

    /**
     *  是否自动开单
     * @param code 订单码
     * @return 是否
     */
    Boolean isAutoOrder(String code);

    /**
     *  是否转采购控制
     * @param code 订单码
     * @return 是否
     */
    Boolean isPurchaseControl(String code);

    /**
     *  是否转{@code SO}控制
     * @param code 订单码
     * @return 是否
     */
    Boolean isSoControl(String code);

    /**
     *  是否转{@code VSO}控制
     * @param code 订单码
     * @return 是否
     */
    Boolean isVsoControl(String code);

    /**
     *  整单开单控制
     * @param code 订单码
     * @return 是否
     */
    Boolean isSingleOrderControl(String code);

    /**
     *  是否检查预售计划量
     * @param code 订单码
     * @return 是否
     */
    Boolean isCheckPreSell(String code);

    /**
     *  so可用量检查（Atp）
     * @param code 订单码
     * @return 是否
     */
    Boolean isAtp(String code);

    /**
     *  是否发送Tms标识
     * @param code 订单码
     * @return 是否
     */
    Boolean isTms(String code);

    /**
     *  销售开单开关集合
     * @param code 订单码
     * @return 销售开单节点需要用到的开关集合
     */
    SaleCreateDTO saleCreateFlag(String code);

    /**
     *  [销售开单]判断是否检查紧缺
     * @param code 订单码
     * @return 是否检查紧缺
     */
    Boolean isSaleShortage(String code);

    /**
     *  [VSO转SO]判断是否检查紧缺
     * @param code 订单码
     * @return 是否检查紧缺
     */
    Boolean isVso2SoShortage(String code);

}
