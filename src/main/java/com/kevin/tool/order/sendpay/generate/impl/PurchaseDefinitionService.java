package com.kevin.tool.order.sendpay.generate.impl;

import com.kevin.tool.order.sendpay.check.RequestContextParam;
import org.springframework.stereotype.Service;

/**
 *  采购订单类型定义（对应码值为1-2） {@link com.kevin.tool.order.sendpay.check.StateConfig#PURCHASE_DEFINITION}
 *  对应配置表为： DEF_PUR_ORDER
 *
 * @author lihongmin
 * @date 2020/6/30 16:31
 * @since 1.0.0
 */
@Service
public class PurchaseDefinitionService implements StageCode {

    @Override
    public String configCode(RequestContextParam param) {
        return "01";
    }

}
