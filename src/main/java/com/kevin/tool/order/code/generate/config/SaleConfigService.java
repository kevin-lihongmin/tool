package com.kevin.tool.order.code.generate.config;

import com.kevin.tool.async.SimpleThreadPool;
import com.kevin.tool.order.code.check.CheckRequestContext;
import com.kevin.tool.order.code.check.RequestContextParam;
import com.kevin.tool.order.code.generate.impl.PresellOrderService;
import com.kevin.tool.order.code.generate.impl.SaleOrderAuditService;
import com.kevin.tool.order.code.generate.impl.ShippingConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
    private static final int TASK = 3;

    /**
     *  默认销售填充值
     *  |<-【销售预订单审核】->|<-【销售订单审核】->|
     */
    private static final String INIT_CODE = "0000000000000000000000";

    private final PresellOrderService presellOrderService;
    private final SaleOrderAuditService saleOrderAuditService;
    private final ShippingConditionService shippingConditionService;

    @Autowired
    public SaleConfigService(PresellOrderService presellOrderService, SaleOrderAuditService saleOrderAuditService,
                             ShippingConditionService shippingConditionService) {
        this.presellOrderService = presellOrderService;
        this.saleOrderAuditService = saleOrderAuditService;
        this.shippingConditionService = shippingConditionService;
    }

    @Override
    public String configCode() {
        // synchronized保证数据回写线程安全
        final StringBuffer sale = new StringBuffer(INIT_CODE);
        ArrayList<Runnable> taskList = new ArrayList<>(TASK);

        final RequestContextParam param = CheckRequestContext.getInstance().get();
        taskList.add(() -> sale.replace(PRE_SELL_AUDIT.getStart(), PRE_SELL_AUDIT.getEnd(), presellOrderService.configCode(param)));
        taskList.add(() -> sale.replace(SALE_AUDIT.getStart(), SALE_AUDIT.getEnd(), saleOrderAuditService.configCode(param)));
        taskList.add(() -> sale.replace(SHIPPING_CONDITION.getStart(), SHIPPING_CONDITION.getEnd(), shippingConditionService.configCode(param)));

        SimpleThreadPool.executeRunnable(CREATE_ORDER, taskList.toArray(new Runnable[0]));
        // 最后标位，来源系统设置
        sale.append(CheckRequestContext.getInstance().getCodeParam().getSourceSystem());
        return sale.toString();
    }

}
