package com.quanyou.qup.biz.check.feign.dto.response;

import lombok.Data;

import java.io.Serializable;

/**
 *  业务账号调用结果明细
 * @author kevin
 * @date 2020/8/13 9:24
 * @since 1.0.0
 */
@Data
public class AccountResultDTO extends ResultDTO implements Serializable {
    /**
     *  序列化标识
     */
    private static final long serialVersionUID = -8097658176938533674L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 账号编码
     */
    private String code;

    /**
     * 账号名称
     */
    private String name;

    /**
     * 简称
     */
    private String accountAbbr;

    /**
     * 客户ID
     */
    private String customerId;

    /**
     * 账号类型
     */
    private String accountType;

    /**
     * 账号类型编码
     */
    private String accountTypeCode;

    /**
     * 账号名称
     */
    private String accountTypeName;

    /**
     * 状态
     */
    private Integer status;

    /**
     * BUSINESS_STATUS
     */
    private String businessStatus;

    /**
     * 客户编码
     */
    private String customerCode;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 一级分类
     */
    private String oneCategory;
    private String oneCategoryName;

    /**
     * 二级分类
     */
    private String twoCategory;

    /**
     * 二级分类名称
     */
    private String twoCategoryName;

    /**
     * 销售订单冻结
     */
    private String soState;

    /**
     * 交货冻结
     */
    private String deliveryState;

    /**
     * 出具发票冻结
     */
    private String invoiceState;

    /**
     * 冻结销售支持
     */
    private String supportState;

    /**
     * 销售组织编码
     */
    private String saleOrgCode;

    /**
     * 销售组织名称
     */
    private String saleOrgName;

    /**
     * 定制客户经理编码
     */
    private String customCode;

    /**
     * 定制客户经理名称
     */
    private String customName;

    /**
     * 定制客户经理电话
     */
    private String customTel;

    /**
     * 成品客户经理编码
     */
    private String finishedProductCode;

    /**
     * 成品客户经理名称
     */
    private String finishedProductName;

    /**
     * 成品客户经理电话
     */
    private String finishedProductTel;
}