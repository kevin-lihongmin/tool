package com.kevin.tool.order.sendpay.generate.config;

import com.kevin.tool.async.SimpleThreadPool;
import com.kevin.tool.order.sendpay.check.CheckRequestContext;
import com.kevin.tool.order.sendpay.check.RequestContextParam;
import com.kevin.tool.order.sendpay.check.StateConfig;
import com.kevin.tool.order.sendpay.generate.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

import static com.kevin.tool.async.SimpleThreadPool.ThreadPoolEnum.CREATE_ORDER;
import static com.kevin.tool.order.sendpay.check.CheckRequestContext.getOrderType;
import static com.kevin.tool.order.sendpay.check.StateConfig.*;
import static com.kevin.tool.order.sendpay.generate.CodeFactory.OrderType.PURCHASE_ORDER;

/**
 *  销售订单配置服务
 *
 * @author lihongmin
 * @date 2020/7/1 10:08
 * @since 1.0.0
 * @see LockSupport#park()
 */
@Service
public class SaleConfigService implements SegmentCode {

    /**
     * 总任务数
     */
    private static final int TASK = 4;

    /**
     *  默认销售填充值
     *  |<-【VSO转SO】->|<-【销售预订单审核】->|<-【销售订单审核】->|
     */
    private static final String INIT_CODE = "0000000000000000000000000000000000";

    /**
     *   订单启始标位
     */
    private static final int SALE_INDEX = VSO_TO_SO.getStart();

    private final Vso2SoService vso2SoService;
    private final PresellOrderService presellOrderService;
    private final SaleOrderAuditService saleOrderAuditService;
    private final ShippingConditionService shippingConditionService;

    @Autowired
    public SaleConfigService(Vso2SoService vso2SoService, PresellOrderService presellOrderService,
                             SaleOrderAuditService saleOrderAuditService, ShippingConditionService shippingConditionService) {
        this.vso2SoService = vso2SoService;
        this.presellOrderService = presellOrderService;
        this.saleOrderAuditService = saleOrderAuditService;
        this.shippingConditionService = shippingConditionService;
    }

    @Override
    public String configCode() throws InterruptedException {
        // synchronized保证数据回写线程安全
        final StringBuffer sale = new StringBuffer(INIT_CODE);
        final int countSize = getOrderType() == PURCHASE_ORDER ? TASK - 1 : TASK;
        ArrayList<Runnable> taskList = new ArrayList<>(countSize);
        final CountDownLatch countDownLatch = new CountDownLatch(countSize);
        final RequestContextParam param = CheckRequestContext.getInstance().get();

        // 直接开销售订单，则该部分使用初始化标位【00】填充
        if (getOrderType() == PURCHASE_ORDER) {
            taskList.add(() -> replaceCode(vso2SoService.configCode(param), VSO_TO_SO, sale, countDownLatch));
        }
        taskList.add(() -> replaceCode(presellOrderService.configCode(param), PRE_SELL_AUDIT, sale, countDownLatch));
        taskList.add(() -> replaceCode(saleOrderAuditService.configCode(param), SALE_AUDIT, sale, countDownLatch));
        taskList.add(() -> replaceCode(shippingConditionService.configCode(param), SHIPPING_CONDITION, sale, countDownLatch));

        SimpleThreadPool.executeRunnable(CREATE_ORDER, taskList.toArray(new Runnable[0]));
        // 最后标位，来源系统设置
        sale.append(CheckRequestContext.getInstance().getCodeParam().getSourceSystem());
        countDownLatch.await();
        return sale.toString();
    }

    /**
     *  替换订单码
     * @param segment 替换的标为
     * @param stateConfig 订单码配置
     * @param sale 编码
     * @param latch 计数器
     */
    private void replaceCode(String segment, StateConfig stateConfig, StringBuffer sale, CountDownLatch latch) {
        sale.replace(stateConfig.getStart() - SALE_INDEX, stateConfig.getEnd() - SALE_INDEX, segment);
        latch.countDown();
    }

}
