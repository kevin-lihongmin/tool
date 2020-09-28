package com.common.enums;

/**
 *  是否删除的枚举
 *
 *  对应所有数据库的 {@code dr} 列
 *
 * @author kevin
 * @date 2020/8/11 9:03
 * @since 1.0.0
 */
public enum DrEnum implements ICustomEnum {

    /** 启动 */
    DELETED(1, "已删除"),

    /** 关闭 */
    NOT_DELETED(0, "未删除");

    /**
     *  编码
     */
    public final int code;

    /**
     *  描述
     */
    public final String name;

    DrEnum(int code, String name) {
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
}
