package com.kevin.tool.order.code.generate.config;

import com.kevin.tool.async.SimpleThreadPool;
import com.kevin.tool.order.code.check.CheckRequestContext;
import com.kevin.tool.order.code.check.RequestContextParam;
import com.kevin.tool.order.code.check.StateConfig;
import com.kevin.tool.order.code.generate.impl.*;
import com.kevin.tool.order.code.generate.param.CodeParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import static com.kevin.tool.async.SimpleThreadPool.ThreadPoolEnum.CREATE_ORDER;
import static com.kevin.tool.order.code.check.StateConfig.*;

/**
 *  销售订单配置服务
 *
 * @author lihongmin
 * @date 2020/7/1 10:08
 * @since 1.0.0
 */
@Service
public class SaleConfigService implements SegmentCode {

    /**
     * 总任务数
     */
    private static final int TASK = 5;

    /**
     *  采购订单结束位置
     */
    private static final int PURCHASE_END = 12;

    private final SaleDefinitionService saleDefinitionService;
    private final SaleOrderCreateService saleOrderCreateService;
    private final PresellOrderService presellOrderService;
    private final SaleOrderAuditService saleOrderAuditService;
    private final ShippingConditionService shippingConditionService;

    @Autowired
    public SaleConfigService(SaleDefinitionService saleDefinitionService, SaleOrderCreateService saleOrderCreateService,
                             PresellOrderService presellOrderService, SaleOrderAuditService saleOrderAuditService,
                             ShippingConditionService shippingConditionService) {
        this.saleDefinitionService = saleDefinitionService;
        this.saleOrderCreateService = saleOrderCreateService;
        this.presellOrderService = presellOrderService;
        this.saleOrderAuditService = saleOrderAuditService;
        this.shippingConditionService = shippingConditionService;
    }

    @Override
    public String configCode() {
        // synchronized
        final StringBuffer sale = new StringBuffer();
        ArrayList<Runnable> taskList = new ArrayList<>(TASK);
        taskList.add(() -> sale.insert(getStart(SALE_DEFINITION), saleDefinitionService.configCode()));
        taskList.add(() -> sale.insert(getStart(SALE_CREATE), saleOrderCreateService.configCode()));
        taskList.add(() -> sale.insert(getStart(PRE_SELL_AUDIT), presellOrderService.configCode()));
        taskList.add(() -> sale.insert(getStart(SALE_AUDIT), saleOrderAuditService.configCode()));
        taskList.add(() -> sale.insert(getStart(SHIPPING_CONDITION), shippingConditionService.configCode()));

        // 最后标位，来源系统设置
        sale.insert(getStart(SOURCE_SYSTEM), CheckRequestContext.getInstance().getCodeParam().getSourceSystem());

        SimpleThreadPool.executeRunnable(CREATE_ORDER, taskList.toArray(new Runnable[0]));
        return sale.toString();
    }

    /**
     *  获取当前的起始位置
     * @param stateConfig 节点配置枚举
     */
    private static Integer getStart(StateConfig stateConfig) {
        return stateConfig.getStart() - PURCHASE_END;
    }

}
