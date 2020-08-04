package com.kevin.tool.order.sendpay;

import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 *  销售开单开关集合
 *
 * @author lihongmin
 * @date 2020/7/21 16:15
 * @since 1.0.0
 */
@ToString
@AllArgsConstructor
public final class SaleCreateDTO {

    /**
     *  是否转采购控制
     */
    public final Boolean isChaseControl;

    /**
     * 是否自动开单
     */
    public final Boolean isAutoOrder;

    /**
     * 是否开单So控制
     */
    public final Boolean isSoControl;

    /**
     *  是否转Vso控制
     */
    public final Boolean isVsoControl;

    /**
     *  是否整单开单控制
     */
    public final Boolean isSingleOrderControl;

}
