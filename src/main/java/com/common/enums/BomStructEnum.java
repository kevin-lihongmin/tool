package com.common.enums;

/**
 * BOM结构
 *
 * @author 抓抓匠
 * @since 2020-08-27
 */
public enum BomStructEnum {
    saleBom(1, "销套结构"),
    suiteBom(2, "套包结构");

    private Integer code;
    private String name;

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    BomStructEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(String code) {
        for (BomStructEnum bomStructEnum : BomStructEnum.values()) {
            if (bomStructEnum.code.equals(code)) {
                return bomStructEnum.name;
            }
        }
        return null;
    }
}
