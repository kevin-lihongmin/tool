package com.common.enums;


/**
 * 销售订单类型枚举。
 *
 * @author fudeling
 * @since 2020/8/12 20:34
 */
public enum SoTypeEnum implements ICustomEnum {
	/***/
	Standard("ZOR1", "全友标准销售订单"),
	DirectSale("ZOR2", "全友直营办销售订单"),
	Agency("ZBH1", "全友合(独)资办销售订单"),
	SinglePackage("ZDD1", "全友单包件销售订单"),
	Alien("ZYH1", "全友销售公司异型订单"),
	Resource("ZZY1", "全友资源销售订单"),
	Decoration("ZZY2", "全友饰品销售订单"),
	Direct("ZZY3", "全友（资源饰品）直送销售订单"),
	Free("ZMF1", "全友（资源）免费销售订单"),
	Patch("ZMF2", "全友售后补件免费订单"),
	SinglePackReturn("ZRD1", "全友单包件退货订单"),
	StandardReturn("ZRE1", "全友标准退货订单"),
	FabricateReturn("ZRE2", "全友制造退货订单"),
	RetailOfficeReturn("ZRE3", "全友直营办退货订单"),
	BathStandardReturn("ZRE4", "卫浴标准退货订单"),
	AgencyReturn("ZRR1", "全友合（独）资办退货订单"),
	ResourceReturn("ZRY1", "全友资源饰品退货订单"),
	Allocate("ZYD2", "全友饰品调拨订单"),
	ResourceAllocate("ZYD1", "全友资源调拨订单"),
	SinglePackAllocate("ZDB3", "全友单包件调拨订单"),
	QyAllocate("ZDB1", "全友调拨订单"),
	QySpecimen("ZYP1", "全友样品销售订单"),
	DzFinCustom("ZYH4", "全友定制成品销售订单");

	private String code;
	private String name;

	SoTypeEnum(String code, String name) {
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


	public static SoTypeEnum getByCode(String code) {
		SoTypeEnum[] values = SoTypeEnum.values();
		for (SoTypeEnum value : values) {
			if (value.code.equals(code)) {
				return value;
			}
		}
		return null;
	}
}
