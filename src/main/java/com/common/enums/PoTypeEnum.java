package com.common.enums;

/**
 *	采购订单类型枚举, 与原来兼容，使用原来的名称
 *
 * @author kevin
 * @date 2020/8/24 15:12
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public enum PoTypeEnum implements ICustomEnum {
	/** 常规采购订单 */
	COMMON("01", "常规采购订单"),

	/** 单包件采购订单 */
	SINGLE_PACKAGE("02", "单包件采购订单"),

	/** 异型采购订单 */
	ALIEN("03", "异型采购订单"),

	/** 资源采购订单 */
	RESOURCE("04", "资源采购订单"),

	/** 饰品采购订单 */
	DECORATION("05", "饰品采购订单"),

	/** 电商采购订单 */
	E_COMMERCE("06", "电商采购订单"),

	/** 补件采购订单 */
	PATCH("07", "补件采购订单"),

	/** 定制采购订单 */
	CUSTOM("08", "定制采购订单"),

	/** 电商常规订单 */
	O_COMMERCE("09", "电商常规订单"),

	/** 电商单包件订单 */
	O_SINGLE_PACKAGE("10", "电商单包件订单"),

	/** 定制窗帘订单 */
	CSS("11", "定制窗帘订单"),

	/** 墙变订单 */
	WALL_C("12", "墙变订单"),

	/** 装企订单 */
	OUT_PURCHASE("15", "装企订单");

	/**
	 * 编码
	 */
	private final String code;

	/**
	 * 名称
	 */
	private final String name;

	PoTypeEnum(String code, String name) {
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
	 * 使用编码获取枚举
	 * @param code 编码
	 * @return 对应的枚举
	 */
	public static PoTypeEnum getByCode(String code) {
		PoTypeEnum[] values = PoTypeEnum.values();
		for (PoTypeEnum value : values) {
			if (value.code.equals(code)) {
				return value;
			}
		}
		return null;
	}

}
