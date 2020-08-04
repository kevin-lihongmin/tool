package com.kevin.tool.order.sendpay.generate.impl;

import com.kevin.tool.order.sendpay.check.RequestContextParam;

/**
 *  订单码服务
 * @author lihongmin
 * @date 2020/7/1 10:12
 * @since 1.0.0
 */
public interface StageCodeImpl {

    /**
     *  分段订单码
     * @param param 上下文参数
     * @return 部分订单码
     */
    String configCode(RequestContextParam param);

}
