package com.common.enums;

/**
 * 产品结构类型
 *
 * @author 抓抓匠
 * @since 2020-08-21
 */
@SuppressWarnings("unused")
public enum ProductStructEnum implements ICustomEnum {

    /** 组合与SKU结构 */
    COMBINATION_STRUCT(1, "组合与SKU结构"),

    /** SKU与产品结构 */
    SKU_STRUCT(2, "SKU与产品结构"),

    /** 销售产品与产品（套件或包件）结构 */
    SALE_STRUCT(3, "销售产品与产品（套件或包件）结构"),

    /** 套件与包件结构 */
    SUITE_STRUCT(4, "套件与包件结构"),

    /** 包件 */
    PACK_STRUCT(5, "包件");

    /**
     * 编码
     */
    private final Integer code;

    /**
     * 名称
     */
    private final String name;

    @Override
    public String getCode() {
        return code + "";
    }

    @Override
    public String getName() {
        return name;
    }

    ProductStructEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 获取枚举名称
     * @param code 枚举编码
     * @return 枚举名称
     */
    public static String getNameByCode(String code) {
        for (ProductStructEnum productStructEnum : ProductStructEnum.values()) {
            if (productStructEnum.getCode().equals(code)) {
                return productStructEnum.name;
            }
        }
        return null;
    }
}
