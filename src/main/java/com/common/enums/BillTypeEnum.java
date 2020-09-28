package com.common.enums;

/**
 * 单据类型(QY062)。
 *
 * @author fudeling
 * @since 2020/8/13 18:53
 */
public enum BillTypeEnum implements ICustomEnum  {
	/***/
	AlienPurchaseApply("01", "异型采购申请"),
	PurchaseApply("02", "采购申请单"),
	PurchaseOrder("03", "采购订单"),
	PreSaleOrder("04", "销售预订单"),
	SaleOrder("05", "销售订单"),
	CustomWardrobe("06", "定制衣柜需求"),
	PatchApply("07", "补件申请单");

	private String code;
	private String name;

	BillTypeEnum(String code, String name) {
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

	public static BillTypeEnum getByCode(String code) {
		BillTypeEnum[] values = BillTypeEnum.values();
		for (BillTypeEnum value : values) {
			if (value.code.equals(code)) {
				return value;
			}
		}
		return null;
	}
}
