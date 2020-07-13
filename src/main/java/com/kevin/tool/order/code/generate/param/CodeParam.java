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
     * 采购订单类型
     */
    private String poOrderType;

    /**
     * 接单组织
     */
    private String receiveOrg;

    /**
     *  客户二级分类
     */
    private String customer2Classify;

    /**
     *  来源系统
     */
    private String sourceSystem;

}
