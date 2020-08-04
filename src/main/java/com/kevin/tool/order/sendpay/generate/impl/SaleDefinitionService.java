package com.kevin.tool.order.sendpay.generate.impl;

import com.kevin.tool.order.sendpay.check.RequestContextParam;
import org.springframework.stereotype.Service;

/**
 *  销售订单定义服务， 依赖于采购订单类型（对应码值为13-14）  {@link com.kevin.tool.order.sendpay.check.StateConfig#SALE_DEFINITION}
 *
 *  对应配置表为：CONF_PUR_TO_SALE_RULE 和 DEF_SALE_ORDER
 *
 * @author lihongmin
 * @date 2020/6/30 16:31
 * @since 1.0.0
 */
@Service
public class SaleDefinitionService implements StageCode {

    @Override
    public String configCode(RequestContextParam param) {
        return "01";
    }

}
