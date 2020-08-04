package com.kevin.tool.order.sendpay.generate.impl;

import com.kevin.tool.order.sendpay.check.RequestContextParam;
import org.springframework.stereotype.Service;

/**
 *  采购订单审核配置服务
 *  对应配置表为： CONF_PUR_ORDER_AUDIT
 *
 * @author lihongmin
 * @date 2020/6/30 16:31
 * @since 1.0.0
 */
@Service
public class PurchaseAuditService implements StageCode {

    @Override
    public String configCode(RequestContextParam param) {
        return "0102010201";
    }

}
