package com.kevin.tool.order.code.generate.param;

import lombok.Data;

/**
 *  订单码参数
 * @author lihongmin
 * @date 2020/6/30 15:02
 * @since 1.0.0
 */
@Data
public class CodeParam {

    /**
     * 经销商Id
     */
    private String customId;

    /**
     * 所在大区
     */
    private String orgId;

    /**
     *  订单编码
     */
    private String orderCode;

    /**
     *  来源系统
     */
    private String sourceSystem;
}
