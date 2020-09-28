package com.common.enums;

/**
 * 产品组
 *
 * @author 抓抓匠
 * @since 2020-08-21
 */
public enum ProductGroupEnum {
    PG_Z0("Z0", "整装业务群"),
    PG_Q0("Q0", "智能墙变产品组"),
    PG_R0("R0", "窗帘产品组"),
    PG_B0("B0", "橱柜产品组"),
    PG_A0("A0", "卫浴产品组"),
    PG_90("90", "全友定制衣柜产品组"),
    PG_80("80", "电子商务产品组"),
    PG_70("70", "全友实木产品组"),
    PG_62("62", "全友饰品产品组"),
    PG_61("61", "全友资源产品组"),
    PG_60("60", "全友售后材料产品组"),
    PG_50("50", "全友非自制OEM产品组"),
    PG_40("40", "全友虚拟半成品产品组"),
    PG_30("30", "全友软体产品组"),
    PG_20("20", "全友板式产品组"),
    PG_11("11", "全友单包件异型产品组"),
    PG_10("10", "全友原材料产品组"),
    PG_00("00", "全友通用产品组");

    private String code;
    private String name;

    ProductGroupEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getName(String code) {
        for (ProductGroupEnum productGroupEnum : ProductGroupEnum.values()) {
            if (productGroupEnum.getCode().equals(code)) {
                return productGroupEnum.getName();
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
