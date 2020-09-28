package com.common.bean;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 产品信息
 *
 * @author 抓抓匠
 * @since 2020-08-26
 */
@Data
public class ProductInfo {
    /**
     * 产品ID
     */
    private String id;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品性质
     */
    private String proNature;

    /**
     * 产品状态
     */
    private String proState;

    /**
     * 毛重
     */
    private BigDecimal roughWeight;

    /**
     * 体积
     */
    private BigDecimal cubage;

    /**
     * 体积单位
     */
    private String volumeUnit;

    /**
     * 异型类型
     */
    private String customizeType;

    /**
     * 物料类型
     */
    private String proType;

    /**
     * 采购控制状态
     */
    private String canBePurchase;

    /**
     * 销售系列编码
     */
    private String saleSeriesCode;

    /**
     * 销售系列名称
     */
    private String saleSeriesName;

    /**
     * 产品组编码
     */
    private String productGroupCode;

    /**
     * 产品组名称
     */
    private String productGroupName;

    /**
     * 物料组编码
     */
    private String materialGroupCode;

    /**
     * 物料组名称
     */
    private String materialGroupName;

    /**
     * 研发系列编码
     */
    private String radSeriesCode;

    /**
     * 研发系列名称
     */
    private String radSeriesName;

    /**
     * 是否销售产品
     */
    private Integer isSaleProduct;

    /**
     * 是否包件
     */
    private Integer isProductPack;

    /**
     * 是否套件
     */
    private Integer isProductSuite;
}
