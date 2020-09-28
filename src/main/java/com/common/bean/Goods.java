package com.common.bean;

import lombok.Data;

/**
 * 商品
 *
 * @author 抓抓匠
 * @since 2020-08-26
 */
@Data
public class Goods {
    /**
     * SPU ID
     */
    private Long spuId;

    /**
     * SPU编码
     */
    private String spuCode;

    /**
     * SPU名称
     */
    private String spuName;

    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * SKU编码
     */
    private String skuCode;

    /**
     * SKU名称
     */
    private String skuName;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * SKU数量
     */
    private Integer skuNum;
}
