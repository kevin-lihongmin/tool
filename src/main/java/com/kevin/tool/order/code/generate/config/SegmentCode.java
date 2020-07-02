package com.kevin.tool.order.code.generate.config;

import com.kevin.tool.order.code.generate.param.CodeParam;

/**
 *  订单码服务
 * @author lihongmin
 * @date 2020/7/1 10:12
 * @since 1.0.0
 */
public interface SegmentCode {

    /**
     *  分段订单码
     * @param codeParam 订单码请求参数
     * @return 部分订单码
     */
    String configCode(CodeParam codeParam);

}
