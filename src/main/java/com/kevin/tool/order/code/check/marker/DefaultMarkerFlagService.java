package com.kevin.tool.order.code.check.marker;

import static com.kevin.tool.order.code.check.StateConfig.*;

/**
 *  默认标位实现
 *
 * @author lihongmin
 * @date 2020/7/12 9:38
 * @since 1.0.0
 */
public class DefaultMarkerFlagService extends AbstractMarkerFlagServiceImpl {

    @Override
    public Boolean isAutoOrder(String code) {
        return super.isFlag(code, AUTO_ORDER);
    }

    @Override
    public Boolean isTms(String code) {
        return super.isFlag(code, SHIPPING_CONDITION);
    }

    @Override
    public Boolean isPurchaseControl(String code) {
        return super.isFlag(code, PURCHASE_CONTROL);
    }

    @Override
    public Boolean isVsoControl(String code) {
        return super.isFlag(code, VSO_CONTROL);
    }

    @Override
    public Boolean isSingleOrderControl(String code) {
        return super.isFlag(code, VSO_CONTROL);
    }
}
