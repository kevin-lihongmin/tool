package com.common.enums;

import lombok.Getter;

/**
 *  是否的枚举
 *
 * @author kevin
 * @date 2020/8/11 9:03
 * @since 1.0.0
 */
@Getter
public enum BooleanEnum implements ICustomEnum {

    /**
     * 是
     */
    YES("true", "是"),

    /**
     * 否
     */
    NO("false", "否");

    /**
     * 是否的字符串标识
     */
    private String code;

    private String name;

    BooleanEnum(String code, String name) {
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

    /**
     *  判断是否为：是
     * @param booleanEnum 枚举类型
     * @return 是否为：是
     */
    public static Boolean yes(BooleanEnum booleanEnum) {
        return booleanEnum == YES;
    }

    /**
     *  判断是否为true
     * @param code 是否的字符串
     * @return 布尔类型
     */
    public static Boolean parse(String code) {
        if (code == null) {
            return false;
        }
        return YES.code.equals(code);
    }

}
