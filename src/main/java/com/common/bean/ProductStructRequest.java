package com.common.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询产品结构请求参数
 *
 * 一般情况下统一线程操作同样对象，当前只保证集合初始化线程安全，不保证存放数据线程安全（创建者自己保证）。
 *
 * @author 抓抓匠
 * @since 2020-08-26
 */
@Data
public class ProductStructRequest {
    /**
     * 组合编码
     */
    private volatile List<String> combinationCodes;

    /**
     * SKU编码
     */
    private volatile List<String> skuCodes;

    /**
     * 产品编码
     */
    private volatile List<String> productCodes;

    /**
     * 添加sku编码
     * @param code sku编码
     */
    public void addSkuCode(String code) {
        if (skuCodes == null) {
            synchronized (this) {
                if (skuCodes == null) {
                    skuCodes = new ArrayList<>(10);
                }
            }
        }
        skuCodes.add(code);
    }

    /**
     * 添加sku组合编码
     * @param code sku组合编码
     */
    public void addCombinationCode(String code) {
        if (combinationCodes == null) {
            synchronized (this) {
                if (combinationCodes == null) {
                    combinationCodes = new ArrayList<>(10);
                }
            }
        }
        combinationCodes.add(code);
    }

    /**
     * 添加 产品（或单包件）编码
     * @param code 产品（或单包件）编码
     */
    public void addProductCode(String code) {
        if (productCodes == null) {
            synchronized (this) {
                if (productCodes == null) {
                    productCodes = new ArrayList<>(10);
                }
            }
        }
        productCodes.add(code);
    }

}
