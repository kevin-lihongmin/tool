package com.kevin.tool.order.code.generate;

import com.kevin.tool.order.code.check.CheckRequestContext;
import com.kevin.tool.order.code.check.RequestContextParam;
import com.kevin.tool.order.code.generate.config.PurchaseConfigService;
import com.kevin.tool.order.code.generate.config.SaleConfigService;
import com.kevin.tool.order.code.generate.param.CodeParam;
import org.springframework.stereotype.Component;

/**
 *  订单码工厂
 * @author lihongmin
 * @date 2020/6/30 15:01
 * @since 1.0.0
 */
@Component
@SuppressWarnings("unused")
public class DefaultCodeFactory implements CodeFactory {

    /**
     * 是否开启缓存
     */
    private static boolean IS_CACHE = true;

    private final DefaultCodeFactory defaultCodeFactory;
    private final PurchaseConfigService purchaseConfigService;
    private final SaleConfigService saleConfigService;

    public DefaultCodeFactory(DefaultCodeFactory defaultCodeFactory,
                              PurchaseConfigService purchaseConfigService, SaleConfigService saleConfigService) {
        this.defaultCodeFactory = defaultCodeFactory;
        this.purchaseConfigService = purchaseConfigService;
        this.saleConfigService = saleConfigService;
    }

    /**
     *  订单工厂，根据订单类型和参数 -> 生成订单编码
     * @param codeParam 订单参数
     * @return 订单编码
     */
    @Override
    public String generateCode(CodeParam codeParam) {
        return defaultCodeFactory.generateCode(codeParam, OrderType.PURCHASE_ORDER);
    }

    /**
     *  订单码工厂，根据订单类型和参数 -> 生成订单编码
     *
     * @param codeParam 订单参数
     * @param orderType 订单类型
     * @return 订单编码
     */
    @Override
    public String generateCode(CodeParam codeParam, OrderType orderType) {
        final StringBuilder orderCode;
        CheckRequestContext.getInstance().set(new RequestContextParam(codeParam, orderType));
        try {
            // 添加采购单码
            orderCode = new StringBuilder(purchaseConfigService.configCode());
            // 添加销售订单码
            orderCode.append(saleConfigService.configCode());
        } finally {
            CheckRequestContext.getInstance().remove();
        }
        return orderCode.toString();
    }

    /**
     *  订单类型
     * @author lihongmin
     * @date 2020/7/1 9:38
     * @since 1.0.0
     */
    public enum OrderType {
        /**
         *  采购订单
         */
        PURCHASE_ORDER,
        /**
         *  销售订单
         */
        SALE_ORDER
    }

    public static boolean isIsCache() {
        return IS_CACHE;
    }

    public static void setIsCache(boolean isCache) {
        IS_CACHE = isCache;
    }
}
