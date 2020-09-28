package com.common.enums;

/**
 * 采购订单状态枚举
 *
 * @author fudeling
 * @since 2020/8/29 14:12
 */
public enum PoStateEnum implements ICustomEnum {
    /***/
    Uncommitted("01", "未提交", "整单状态"),
    Committed("02", "已提交", "整单状态"),
    Approved("03", "已审核", "整单状态"),
    Finished("05", "已完成", "整单状态"),

    RowUncommitted("06", "未提交", "行状态"),
    RowCommitted("07", "已提交", "行状态"),
    RowApproved("08", "已审核", "行状态"),
    RowPartProcessed("09", "部分处理", "行状态"),
    RowPartRefused("10", "部分拒绝", "行状态"),
    RowRefused("11", "已拒绝", "行状态"),
    RowFinished("12", "已完成", "行状态");

    private final String code;
    private final String name;
    private final String description;

    PoStateEnum(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public static PoStateEnum getByCode(String code) {
        for (PoStateEnum poStateEnum : PoStateEnum.values()) {
            if (poStateEnum.getCode().equals(code)) {
                return poStateEnum;
            }
        }
        return null;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
