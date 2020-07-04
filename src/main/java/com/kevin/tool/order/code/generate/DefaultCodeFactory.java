package com.kevin.tool.order.code.generate;

import com.kevin.tool.order.code.generate.impl.UserConfigService;
import com.kevin.tool.order.code.generate.config.PurchaseConfigService;
import com.kevin.tool.order.code.generate.config.SaleConfigService;
import com.kevin.tool.order.code.generate.param.CodeParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  订单码工厂
 * @author lihongmin
 * @date 2020/6/30 15:01
 * @since 1.0.0
 */
@Component
//@ConditionalOnMissingBean(value = {CodeApplicationContext.class}, search = SearchStrategy.CURRENT)
public class DefaultCodeFactory implements CodeFactory {

    /**
     *  初始化的采购订单码值
     */
    private static final String INIT_CODE = "0000000000000000000000000000000";

    @Autowired
    private DefaultCodeFactory defaultCodeFactory;

    @Autowired
    private PurchaseConfigService purchaseConfigService;

    @Autowired
    private SaleConfigService saleConfigService;

    /**
     * 模拟任务
     */
    private UserConfigService userConfigService;

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
     *  订单工厂，根据订单类型和参数 -> 生成订单编码
     *
     * @param codeParam 订单参数
     * @param orderType 订单类型
     * @return 订单编码
     */
    @Override
    public String generateCode(CodeParam codeParam, OrderType orderType) {
        final StringBuilder orderCode;
        // 添加采购单码
        if (orderType == OrderType.PURCHASE_ORDER) {
            orderCode = new StringBuilder(purchaseConfigService.configCode(codeParam));
        } else {
            orderCode = new StringBuilder(INIT_CODE);
        }
        // 添加销售订单码
        orderCode.append(saleConfigService.configCode(codeParam));

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
        SALE_ORDER;
    }

}
