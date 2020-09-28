package com.common.enums;

/**
 * 类型 1:组合、2:SKU、3:产品（单包件）
 *
 * 对应 PO、SO、VSO、退单的 第二层表中的{@code type} 字段
 *
 * @author fudeling
 * @since 2020/8/26 16:52
 */
@SuppressWarnings("unused")
public enum SkuTypeEnum implements ICustomEnum {
    /**
     * sku组合
     */
    COMBINE("1", "组合"),

    /**
     * sku
     */
    SKU("2", "SKU"),

    /**
     * 产品（单包件）
     */
    SINGLE_PACK("3", "产品（单包件）");

    /**
     * 编码
     */
    private final String code;

    /**
     * 名称
     */
    private final String name;

    SkuTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    public static SkuTypeEnum getByCode(String code) {
        SkuTypeEnum[] values = SkuTypeEnum.values();
        for (SkuTypeEnum value : values) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
