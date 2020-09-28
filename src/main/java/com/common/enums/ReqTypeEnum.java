package com.common.enums;


/**
 * 需求类型枚举(QY051)。
 *
 * @author fudeling
 * @since 2020/8/17 18:07
 */
public enum ReqTypeEnum implements ICustomEnum {
	/***/
    StockUp("01", "备货需求"),
    Retail("02", "零售需求"),
    Activity("03", "活动需求"),
    Sample("04", "上样需求"),
    NewSample("05", "新店上样");

    private String code;
    private String name;

	ReqTypeEnum(String code, String name) {
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

    public static ReqTypeEnum getByCode(String code) {
		ReqTypeEnum[] values = ReqTypeEnum.values();
        for (ReqTypeEnum value : values) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
