package com.kevin.tool.order.sendpay.generate;

import com.kevin.tool.order.sendpay.check.RequestContextParam;
import com.kevin.tool.order.sendpay.generate.config.PurchaseConfigService;
import com.kevin.tool.order.sendpay.generate.config.SaleConfigService;
import com.kevin.tool.order.sendpay.generate.param.CodeParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.kevin.tool.order.sendpay.check.CheckRequestContext.getInstance;

/**
 *  订单码工厂
 * @author lihongmin
 * @date 2020/6/30 15:01
 * @since 1.0.0
 */
@Slf4j
@Component
@SuppressWarnings("unused")
public class DefaultCodeFactory implements CodeFactory {

    /**
     * 是否开启缓存
     */
    private volatile static boolean IS_CACHE = false;

    /**
     * 当前订单码的总长度
     */
    private static final int CURRENT_CODE_SIZE = 72;

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
     *  <p> 采购订单码和销售订单码先串行，后续有性能问题可以考虑并行
     *
     * @param codeParam 订单参数
     * @param orderType 订单类型
     * @return 订单码
     */
    @Override
    public String generateCode(CodeParam codeParam, OrderType orderType) {
        StringBuilder orderCode;
        getInstance().set(new RequestContextParam(codeParam, orderType));
        try {
            orderCode = new StringBuilder(CURRENT_CODE_SIZE)
                    // 添加采购单码
                    .append(purchaseConfigService.configCode())
                    // 添加销售订单码
                    .append(saleConfigService.configCode());
        } catch (InterruptedException e) {
            log.error("get order code InterruptedException");
            return "";
        } finally {
            getInstance().remove();
        }
        return orderCode.toString();
    }

    public static boolean isIsCache() {
        return IS_CACHE;
    }

    public static void setIsCache(boolean isCache) {
        IS_CACHE = isCache;
    }
}
