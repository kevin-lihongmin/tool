package com.kevin.tool.order.code.check.marker;

import com.kevin.tool.order.code.SaleCreateDTO;
import com.kevin.tool.order.code.check.StateConfig;

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
    public Boolean isPurchaseControl(String code) {
        return super.isFlag(code, PURCHASE_CONTROL);
    }

    @Override
    public Boolean isSoControl(String code) {
        return  super.isFlag(code, SO_CONTROL);
    }

    @Override
    public Boolean isVsoControl(String code) {
        return super.isFlag(code, VSO_CONTROL);
    }

    @Override
    public Boolean isAtp(String code) {
        return super.isFlag(code, SO_ATP_CONTROL);
    }

    @Override
    public Boolean isCheckPreSell(String code) {
        return super.isFlag(code, PRE_SELL_CONTROL);
    }

    @Override
    public Boolean isSingleOrderControl(String code) {
        return super.isFlag(code, VSO_CONTROL);
    }

    @Override
    public Boolean isTms(String code) {
        return super.isFlag(code, SHIPPING_CONDITION);
    }

    @Override
    public SaleCreateDTO saleCreateFlag(String code) {
        return new SaleCreateDTO(
                isPurchaseControl(code),
                isAutoOrder(code),
                isSoControl(code),
                isVsoControl(code),
                isSingleOrderControl(code)
        );
    }

    @Override
    public Boolean isSaleShortage(String code) {
        return super.isFlag(code, SALE_SHORTAGE);
    }

    @Override
    public Boolean isVso2SoShortage(String code) {
        return super.isFlag(code, VSO_TO_SO_SHORTAGE);
    }

}
