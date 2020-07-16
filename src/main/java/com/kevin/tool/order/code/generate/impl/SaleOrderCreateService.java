package com.kevin.tool.order.code.generate.impl;

import com.kevin.tool.order.code.check.RequestContextParam;
import org.springframework.stereotype.Service;

/**
 *  销售订单开单检查服务（对应码值为15-36） {@link com.kevin.tool.order.code.check.StateConfig#SALE_CREATE}
 *
 *  对应配置表为：CONF_SALE_ORDER
 *
 * @author lihongmin
 * @date 2020/6/30 16:31
 * @since 1.0.0
 * @see com.kevin.tool.order.code.check.StateConfig#SALE_CREATE
 */
@Service
public class SaleOrderCreateService implements SegmentCodeImpl {

    @Override
    public String configCode(RequestContextParam param) {
        return "0101020201020102010201";
    }

}
