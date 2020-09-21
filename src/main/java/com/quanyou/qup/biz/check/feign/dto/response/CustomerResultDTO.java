package com.quanyou.qup.biz.check.feign.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *  经销商 明细
 *
 * @author kevin
 * @date 2020/8/11 15:23
 * @since 1.0.0
 */
@Data
public class CustomerResultDTO extends ResultDTO implements Serializable {

    private static final long serialVersionUID = -1092136370994731408L;

    /**
     * 客户ID
     */
    private String id;

    /**
     * 客户编码
     */
    private String customerCode;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 办事处编码
     */
    private String officeCode;

    /**
     * 办事处名称
     */
    private String officeName;

    /**
     * 上级客户编码
     */
    private String parentCode;

    /**
     * 上级客户名称
     */
    private String parentName;

    /**
     * 客户经营类型（经营范围）
     */
    private String businessScope;

    /**
     * 客户经营状态
     */
    private String businessStatus;

    /**
     * 客户一级分类
     */
    private String oneCategory;
    private String oneCategoryName;

    /**
     * 客户二级分类
     */
    private String twoCategory;
    private String twoCategoryName;

    /**
     * 状态
     */
    private String status;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 联系电话
     */
    private String contactPhone;

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

    /**
     * 合作起始日期
     */
    private String cooperateStart;

    /**
     * 合作终止日期
     */
    private String cooperateEnd;

    /**
     * 城市层级
     */
    private String cityLevel;

    /**
     * 是否缴纳保证金
     */
    private Integer isDeposit;

    /**
     * 总经营面积
     */
    private BigDecimal totalOperateArea;

    /**
     * 总门头面积
     */
    private BigDecimal totalHeadArea;
}