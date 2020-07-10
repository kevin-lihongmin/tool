package com.kevin.tool.order.code.generate.impl;

import org.springframework.stereotype.Service;

/**
 *  运装条件服务（对应码值为57-58） {@link com.kevin.tool.order.code.check.StateConfig#SHIPPING_CONDITION}
 *
 *  对应配置表为：CONF_SHIPPING_CONDITION 和 DEF_SHIPPING_CONDITION
 *
 * @author lihongmin
 * @date 2020/6/30 16:31
 * @since 1.0.0
 * @see com.kevin.tool.order.code.check.StateConfig#SHIPPING_CONDITION
 */
@Service
public class ShippingConditionService implements SegmentCodeImpl {

    @Override
    public String configCode() {
        return "01";
    }

}