package com.kevin.tool.order.sendpay.generate.config;

import com.kevin.tool.async.SimpleThreadPool;
import com.kevin.tool.order.sendpay.check.CheckRequestContext;
import com.kevin.tool.order.sendpay.check.RequestContextParam;
import com.kevin.tool.order.sendpay.check.StateConfig;
import com.kevin.tool.order.sendpay.generate.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

import static com.kevin.tool.async.SimpleThreadPool.THREAD_POOL_EXECUTOR_MAP;
import static com.kevin.tool.async.SimpleThreadPool.ThreadPoolEnum.CREATE_ORDER;
import static com.kevin.tool.order.sendpay.check.CheckRequestContext.getOrderType;
import static com.kevin.tool.order.sendpay.check.StateConfig.*;
import static com.kevin.tool.order.sendpay.generate.CodeFactory.OrderType.PURCHASE_ORDER;
import static java.util.concurrent.CompletableFuture.allOf;
import static java.util.concurrent.CompletableFuture.runAsync;

/**
 *  销售订单配置服务
 *
 * @author lihongmin
 * @date 2020/7/1 10:08
 * @since 1.0.0
 * @see LockSupport#park()
 * @see LockSupport#unpark(Thread)
 */
@Service
@SuppressWarnings("unused")
public class SaleConfigService implements SegmentCode {

    /**
     * 总任务数
     */
    private static final int TASK = 4;

    /**
     *  默认销售填充值
     *  |<-【VSO转SO】->|<-【销售预订单审核】->|<-【销售订单审核】->|<-【装运条件】->|
     */
    private static final String INIT_CODE = "0000000000" + "0000000000" + "0000000000" + "00";

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

    /**
     * @see CountDownLatch 协调线程
     * @see ThreadPoolExecutor 线程池
     */
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
        countDownLatch.await();
        // 最后标位，来源系统设置
        sale.append(CheckRequestContext.getInstance().getCodeParam().getSourceSystem());
        return sale.toString();
    }

    /**
     * @see ThreadPoolExecutor#invokeAll(Collection, long, TimeUnit) 线程池的阻塞获取结果方法
     */
    public String configCode2() {
        // synchronized保证数据回写线程安全
        final StringBuffer sale = new StringBuffer(INIT_CODE);
        final int countSize = getOrderType() == PURCHASE_ORDER ? TASK - 1 : TASK;
        ArrayList<Callable<Object>> taskList = new ArrayList<>(countSize);
        final RequestContextParam param = CheckRequestContext.getInstance().get();

        // 直接开销售订单，则该部分使用初始化标位【00】填充
        if (getOrderType() == PURCHASE_ORDER) {
            taskList.add(() -> invoke(vso2SoService, sale, VSO_TO_SO, param));
        }
        taskList.add(() -> invoke(presellOrderService, sale, PRE_SELL_AUDIT, param));
        taskList.add(() -> invoke(saleOrderAuditService, sale, SALE_AUDIT, param));
        taskList.add(() -> invoke(shippingConditionService, sale, SHIPPING_CONDITION, param));

        SimpleThreadPool.executeAll(CREATE_ORDER, taskList);
        // 最后标位，来源系统设置
        sale.append(CheckRequestContext.getInstance().getCodeParam().getSourceSystem());
        return sale.toString();
    }

    /**
     * @see ExecutorCompletionService 批量执行器
     * @see ThreadPoolExecutor 线程池
     * @see LinkedBlockingDeque 阻塞队列, 默认初始化的长度为: 2147483647 {@link Integer#MAX_VALUE}
     */
    public String configCode3() throws InterruptedException, ExecutionException {
        // synchronized保证数据回写线程安全
        final StringBuffer sale = new StringBuffer(INIT_CODE);
        final int countSize = getOrderType() == PURCHASE_ORDER ? TASK - 1 : TASK;
        final RequestContextParam param = CheckRequestContext.getInstance().get();

        ExecutorCompletionService<Object> service = new ExecutorCompletionService(THREAD_POOL_EXECUTOR_MAP.get(CREATE_ORDER), new LinkedBlockingDeque<>(4));

        // 直接开销售订单，则该部分使用初始化标位【00】填充
        if (getOrderType() == PURCHASE_ORDER) {
            service.submit(() -> invoke(vso2SoService, sale, VSO_TO_SO, param));
        }
        service.submit(() -> invoke(presellOrderService, sale, PRE_SELL_AUDIT, param));
        service.submit(() -> invoke(saleOrderAuditService, sale, SALE_AUDIT, param));
        service.submit(() -> invoke(shippingConditionService, sale, SHIPPING_CONDITION, param));

        for (int size = countSize; size > 0; size--) {
            service.take().get();
        }
        // 最后标位，来源系统设置
        sale.append(CheckRequestContext.getInstance().getCodeParam().getSourceSystem());
        return sale.toString();
    }

    /**
     * @see ThreadPoolExecutor 线程池
     * @see CompletableFuture 异步链式编程
     */
    public String configCode4() {
        // synchronized保证数据回写线程安全
        final StringBuffer sale = new StringBuffer(INIT_CODE);
        final int countSize = getOrderType() == PURCHASE_ORDER ? TASK - 1 : TASK;
        final RequestContextParam param = CheckRequestContext.getInstance().get();
        ThreadPoolExecutor executor = THREAD_POOL_EXECUTOR_MAP.get(CREATE_ORDER);
        List<CompletableFuture<Void>> task = new ArrayList<>(countSize);

        // 直接开销售订单，则该部分使用初始化标位【00】填充
        if (getOrderType() == PURCHASE_ORDER) {
            task.add(runAsync(() -> invoke(vso2SoService, sale, VSO_TO_SO, param), executor));
        }
        task.add(runAsync(() -> invoke(presellOrderService, sale, PRE_SELL_AUDIT, param), executor));
        task.add(runAsync(() -> invoke(saleOrderAuditService, sale, SALE_AUDIT, param), executor));
        task.add(runAsync(() -> invoke(shippingConditionService, sale, SHIPPING_CONDITION, param), executor));

        allOf(task.toArray(new CompletableFuture[0]))
                // 最后标位，来源系统设置
                .thenApply(obj -> sale.append(CheckRequestContext.getInstance().getCodeParam().getSourceSystem()));
        return sale.toString();
    }

    private Object invoke(StageCode service, StringBuffer sale, StateConfig stateConfig, RequestContextParam param) {
        String segment = service.configCode(param);
        sale.replace(stateConfig.getStart() - SALE_INDEX, stateConfig.getEnd() - SALE_INDEX, segment);
        return null;
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
    }

}
