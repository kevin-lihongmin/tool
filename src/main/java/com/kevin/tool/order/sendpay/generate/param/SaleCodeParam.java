package com.kevin.tool.order.sendpay.generate.param;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  销售订单
 * @author lihongmin
 * @date 2020/7/1 9:43
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SaleCodeParam extends CodeParam {

    /**
     *  运装条件编码
     */
    private String shippingConditionCode;

    /**
     *  是否免费
     */
    private Boolean isFree;

    /**
     *  是否直发
     */
    private Boolean isDirect;

}
