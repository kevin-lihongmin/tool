package com.kevin.tool.order.sendpay.generate.impl;

import com.kevin.tool.order.sendpay.check.RequestContextParam;
import org.springframework.stereotype.Service;

/**
 *  销售订单<b>审核<b/>检查服务（对应码值为47-56） {@link com.kevin.tool.order.sendpay.check.StateConfig#SALE_AUDIT}
 *
 *  对应配置表为：CONF_SALE_ORDER_AUDIT
 *
 * @author lihongmin
 * @date 2020/6/30 16:31
 * @since 1.0.0
 * @see com.kevin.tool.order.sendpay.check.StateConfig#SALE_AUDIT
 */
@Service
public class SaleOrderAuditService implements StageCodeImpl {

    @Override
    public String configCode(RequestContextParam param) {
        return "0101020201";
    }

}
