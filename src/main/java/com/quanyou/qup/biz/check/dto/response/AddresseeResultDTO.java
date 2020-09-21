package com.quanyou.qup.biz.check.dto.response;

import lombok.Data;

import java.io.Serializable;

/**
 *  送达方
 * @author kevin
 * @date 2020/8/13 10:11
 * @since 1.0.0
 */
@Data
public class AddresseeResultDTO extends ResultDTO implements Serializable {

    /**
     *  序列化标识
     */
    private static final long serialVersionUID = 1885054679100620871L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 送达方编码
     */
    private String code;

    /**
     * 送达方名称
     */
    private String name;

    /**
     * 送达方状态
     */
    private String status;

    /**
     * 业务账号编码
     */
    private String accountCode;

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

    /**
     * 一级分类名称
     */
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
     * 客户营业状态
     */
    private String customerBusinessStatus;
}