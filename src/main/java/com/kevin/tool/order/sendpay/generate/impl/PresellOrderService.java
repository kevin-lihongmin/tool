package com.kevin.tool.order.sendpay.generate.impl;

import com.kevin.tool.order.sendpay.check.RequestContextParam;
import org.springframework.stereotype.Service;

/**
 *  预销售订单开单检查服务（对应码值为37-46） {@link com.kevin.tool.order.sendpay.check.StateConfig#PRE_SELL_AUDIT}
 *
 *  对应配置表为：CONF_SALE_PRESELL_ORDER_AUDIT
 *
 * @author lihongmin
 * @date 2020/6/30 16:31
 * @since 1.0.0
 * @see com.kevin.tool.order.sendpay.check.StateConfig#PRE_SELL_AUDIT
 */
@Service
public class PresellOrderService implements StageCodeImpl {

    @Override
    public String configCode(RequestContextParam param) {
        return "0101020201";
    }

}
