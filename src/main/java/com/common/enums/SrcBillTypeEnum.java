package com.common.enums;

/**
 * 来源单据类型
 *
 * @author 抓抓匠
 * @since 2020-09-16
 */
public enum SrcBillTypeEnum {
    ALIEN("01", "异形采购申请单"),
    APPLY("02", "采购申请单"),
    PO("03", "采购订单"),
    VSO("04", "销售预订单"),
    SO("05", "销售订单"),
    CCS("06", "定制需求"),
    PATCH("07", "补件申请单"),
    NULL("08", "无"),
    EC("09", "电商采购");

    private final String code;
    private final String name;

    SrcBillTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static SrcBillTypeEnum getByCode(String code) {
        SrcBillTypeEnum[] values = SrcBillTypeEnum.values();
        for (SrcBillTypeEnum value : values) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
