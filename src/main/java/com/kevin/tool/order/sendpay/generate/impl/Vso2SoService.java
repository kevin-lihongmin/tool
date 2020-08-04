package com.kevin.tool.order.sendpay.generate.impl;

import com.kevin.tool.order.sendpay.check.RequestContextParam;
import org.springframework.stereotype.Service;

/**
 *  VSO转SO 检查服务 {@link com.kevin.tool.order.sendpay.check.StateConfig#VSO_TO_SO}
 *
 *  对应配置表为：CONF_VSO_TO_SO
 *
 * @author lihongmin
 * @date 2020/6/30 16:31
 * @since 1.0.0
 * @see com.kevin.tool.order.sendpay.check.StateConfig#VSO_TO_SO
 */
@Service
public class Vso2SoService implements StageCodeImpl {

    @Override
    public String configCode(RequestContextParam param) {
        return "0101020201";
    }

}
