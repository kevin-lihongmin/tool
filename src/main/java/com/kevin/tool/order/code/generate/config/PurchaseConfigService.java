package com.kevin.tool.order.code.generate.config;

import com.kevin.tool.async.SimpleThreadPool;
import com.kevin.tool.order.code.check.CheckRequestContext;
import com.kevin.tool.order.code.check.RequestContextParam;
import com.kevin.tool.order.code.generate.DefaultCodeFactory;
import com.kevin.tool.order.code.generate.impl.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static com.kevin.tool.async.SimpleThreadPool.ThreadPoolEnum.CREATE_ORDER;
import static com.kevin.tool.order.code.check.CheckRequestContext.getOrderType;
import static com.kevin.tool.order.code.check.StateConfig.*;
import static com.kevin.tool.order.code.generate.CodeFactory.OrderType.PURCHASE_ORDER;

/**
 *  采购订单配置服务
 *
 *  <p>
 *      设置默认初始值，后续使用多线程并行获取订单码标位值，{@link StringBuffer#replace(int, int, String)} 使用synchronized关键字保证回写的数据安全
 *  <p>
 *      多线程执行获取订单码任务
 *
 *
 * @author lihongmin
 * @date 2020/7/1 10:07
 * @since 1.0.0
 */
@Service
public class PurchaseConfigService implements SegmentCode, InitializingBean, ApplicationContextAware {

    /**
     * 总任务数
     */
    private static final int TASK = 3;

    /**
     *  默认审核填充值
     *  |<-【1-2采购订单定义】->|<-【3-12采购订单审核】->|<-【13-14销售订单定义】->|<-【15-38销售开单配置】->|
     */
    private static final String INIT_CODE = "00000000000000000000000000000000000000";

    private ApplicationContext applicationContext;

    private final PurchaseDefinitionService purchaseDefinitionService;
    private final PurchaseAuditService purchaseAuditService;
    private final SaleDefinitionService saleDefinitionService;
    private final SaleOrderCreateService saleOrderCreateService;

    @Autowired
    public PurchaseConfigService(PurchaseDefinitionService purchaseDefinitionService, PurchaseAuditService purchaseAuditService,
                                 SaleDefinitionService saleDefinitionService, SaleOrderCreateService saleOrderCreateService) {
        this.purchaseDefinitionService = purchaseDefinitionService;
        this.purchaseAuditService = purchaseAuditService;
        this.saleDefinitionService = saleDefinitionService;
        this.saleOrderCreateService = saleOrderCreateService;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() {
        if (DefaultCodeFactory.isIsCache()) {
            BeanFactory beanFactory = applicationContext;
            CacheSegmentCodeImpl cacheSegmentCodeImpl = beanFactory.getBean(CacheSegmentCodeImpl.class);
            cacheSegmentCodeImpl.setDelegate(this.purchaseDefinitionService);
//            this.purchaseDefinitionService = cacheSegmentCodeImpl;
        }
    }

    @Override
    public String configCode() throws InterruptedException {
        // synchronized保证数据回写线程安全
        final StringBuffer purchase = new StringBuffer(INIT_CODE);
        List<Runnable> taskList = new ArrayList<>(TASK);
        final int countSize = getOrderType() == PURCHASE_ORDER ? 1 : TASK;
        final CountDownLatch countDownLatch = new CountDownLatch(countSize);

        final RequestContextParam param = CheckRequestContext.getInstance().get();
        taskList.add(() -> {
            // 串行执行订单定义，前后有依赖关系
            purchase.replace(PURCHASE_DEFINITION.getStart(), PURCHASE_DEFINITION.getEnd(), purchaseDefinitionService.configCode(param));
            purchase.replace(SALE_DEFINITION.getStart(), SALE_DEFINITION.getStart(), saleDefinitionService.configCode(param));
            countDownLatch.countDown();
        });
        if (getOrderType() == PURCHASE_ORDER) {
            taskList.add(() -> {
                purchase.replace(PURCHASE_AUDIT.getStart(), PURCHASE_AUDIT.getEnd(), purchaseAuditService.configCode(param));
                countDownLatch.countDown();
            });
            taskList.add(() -> {
                purchase.replace(SALE_CREATE.getStart(), SALE_CREATE.getEnd(), saleOrderCreateService.configCode(param));
                countDownLatch.countDown();
            });
        }

        SimpleThreadPool.executeRunnable(CREATE_ORDER, taskList.toArray(new Runnable[0]));
        countDownLatch.await();
        return purchase.toString();
    }

}
