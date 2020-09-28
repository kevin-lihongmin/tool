package com.common.bean;

import com.quanyou.qup.middle.common.enums.ProductStructEnum;
import lombok.Data;

import java.util.List;

/**
 * 产品（SKU组合、SKU、销售产品、套件、包件）结构
 *
 * @author 抓抓匠
 * @since 2020-08-26
 */
@Data
public class ProductStruct {
    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     * 1；组合与SKU结构
     * 2：SKU与产品结构
     * 3：销售产品与产品（套件或包件）结构
     * 4：套件与包件结构
     * 5：包件
     */
    private ProductStructEnum type;

    /**
     * 数量（结构下数量）
     */
    private Integer num;

    /**
     * 组合商品
     */
    private Combination combination;

    /**
     * 商品
     */
    private Goods goods;

    /**
     * 产品信息
     */
    private ProductInfo productInfo;

    /**
     * 下级产品结构
     */
    private List<ProductStruct> children;

    public ProductStruct(Combination combination, List<ProductStruct> children) {
        this.combination = combination;
        this.code = combination.getCode();
        this.name = combination.getName();
        this.type = ProductStructEnum.COMBINATION_STRUCT;
        this.num = 1;
        this.children = children;
    }

    public ProductStruct(Goods goods, int num, List<ProductStruct> children) {
        this.goods = goods;
        this.code = goods.getSkuCode();
        this.name = goods.getSkuName();
        this.type = ProductStructEnum.SKU_STRUCT;
        this.num = num;
        this.children = children;
    }

    public ProductStruct(ProductInfo productInfo, ProductStructEnum productStruct, int num, List<ProductStruct> children) {
        this.productInfo = productInfo;
        this.code = productInfo.getProductCode();
        this.name = productInfo.getProductName();
        this.type = productStruct;
        this.num = num;
        this.children = children;
    }
}
