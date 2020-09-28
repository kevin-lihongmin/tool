package com.common.enums;

/**
 * 商品属性类型枚举
 *
 * @author 抓抓匠
 * @since 2020-08-21
 */
public enum AttrTypeEnum {
    /***/
    goodsAttribute("goodsAttribute", "商品属性"),
    specsParam("specsParam", "规格参数"),
    specsAttribute("specsAttribute", "规格属性");

    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    AttrTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }


    public static String getNameByCode(String code) {
        for (AttrTypeEnum attrTypeEnum : AttrTypeEnum.values()) {
            if (attrTypeEnum.code.equals(code)) {
                return attrTypeEnum.name;
            }
        }
        return null;
    }

}
