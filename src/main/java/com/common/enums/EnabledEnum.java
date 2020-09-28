package com.common.enums;

/**
 *  启停用的枚举
 *
 * @author kevin
 * @date 2020/8/11 9:03
 * @since 1.0.0
 */
public enum EnabledEnum implements ICustomEnum {

    /** 启用 */
    ENABLE(1, "启用"),

    /** 停用 */
    DISABLE(0, "停用");

    /**
     *  编码
     */
    public int code;

    /**
     *  名称
     */
    public String name;

    /**
     *  启用编码值
     */
    private static final String ENABLE_CODE = EnabledEnum.ENABLE.code + "";

    EnabledEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getCode() {
        return code + "";
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     *  是否启用状态
     * @param code 启停用状态
     * @return 是否启用状态
     */
    public static Boolean isEnabled(String code) {
        return ENABLE_CODE.equals(code);
    }

    /**
     *  是否启用状态
     * @param code 启停用状态
     * @return 是否启用状态
     */
    public static Boolean isEnabled(Integer code) {
        if (code == null) {
            return false;
        }
        return EnabledEnum.ENABLE.code == code;
    }
}
