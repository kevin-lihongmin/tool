package com.common.enums;

/**
 *	销售产品状态枚举
 *
 * @author kevin
 * @date 2020/8/28 11:30
 * @since 1.0.0
 */
public enum ProductStateEnum implements ICustomEnum {

	/** 未投产 */
	NON_PRODUCING("A", "未投产"),

	/** 在产 */
	IN_PRODUCING("E", "在产"),

	/** 预退市 */
	PRE_DELISTING("C", "预退市"),

	/** 退市 */
	DELISTED("B", "退市"),

	/** 停销 */
	STOP_SELLING("D", "停销");

	/**
	 * 编码
	 */
	private final String code;

	/**
	 * 名称
	 */
	private final String name;

	ProductStateEnum(String code, String name) {
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
	 * 判断是否为正常状态的产品
	 * @param code 状态编码
	 * @return 是否正常
	 */
	public static Boolean isNormal(String code) {
		return IN_PRODUCING.code.equals(code);
	}


	public static String findNameByCode(String code) {
		for (ProductStateEnum value : values()) {
			if (value.getCode().equals(code)) {
				return value.getName();
			}
		}
		return null;
	}
}