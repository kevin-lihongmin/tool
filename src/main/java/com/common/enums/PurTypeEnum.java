package com.common.enums;

/**
 * 要货类型
 *
 * @author 抓抓匠
 * @since 2020-09-15
 */
public enum PurTypeEnum {
    Common(1, "非独资办下属经销商先全友要货"),
    IndependentCustomer(2, "独资办下属经销商向独资办要货"),
    IndependentAgency(3, "独资办向全友要货");

    private Integer code;
    private String name;

    PurTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static PurTypeEnum getByCode(Integer code) {
        PurTypeEnum[] values = PurTypeEnum.values();
        for (PurTypeEnum value : values) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
