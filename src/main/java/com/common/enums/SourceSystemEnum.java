package com.common.enums;

/**
 * 来源系统(QY074)
 *
 * @author fudeling
 * @since 2020/8/11 16:23
 */
public enum SourceSystemEnum implements ICustomEnum{
    /**
     * 终端系统
     */
    TERMINAL("01", "终端系统"),
    E_COMMERCE_PLATFORM("02", "电商平台"),
    SAP("03", "SAP系统"),
    MIDDLE_GROUND("04", "中台系统"),
    BPM("05", "BPM系统"),
    CCS("06", "CCS系统"),
    CRM("07", "CRM系统");

    /**
     *  编码
     */
    private final String code;
    /**
     *  名称
     */
    private final String name;

    SourceSystemEnum(String code, String name) {
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

    public static SourceSystemEnum getByCode(String code) {
        SourceSystemEnum[] values = SourceSystemEnum.values();
        for (SourceSystemEnum value : values) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
