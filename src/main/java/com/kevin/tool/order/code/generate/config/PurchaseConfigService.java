package com.kevin.tool.order.code.generate.config;

import com.kevin.tool.async.SimpleThreadPool;
import com.kevin.tool.order.code.check.CheckRequestContext;
import com.kevin.tool.order.code.generate.DefaultCodeFactory;
import com.kevin.tool.order.code.generate.impl.CachePurchaseDefinitionService;
import com.kevin.tool.order.code.generate.impl.PurchaseDefinitionService;
import com.kevin.tool.order.code.generate.param.CodeParam;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import static com.kevin.tool.async.SimpleThreadPool.ThreadPoolEnum.CREATE_ORDER;
import static com.kevin.tool.order.code.check.StateConfig.PURCHASE_AUDIT;
import static com.kevin.tool.order.code.check.StateConfig.PURCHASE_DEFINITION;
import static com.kevin.tool.order.code.generate.DefaultCodeFactory.OrderType.PURCHASE_ORDER;

/**
 *  采购订单配置服务
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
    private static final int TASK = 2;

    /**
     *  默认审核填充值
     */
    private static final String INIT_CODE = "0000000000";

    private ApplicationContext applicationContext;

    @Autowired
    private PurchaseDefinitionService purchaseDefinitionService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() {
        if (DefaultCodeFactory.isIsCache()) {
            BeanFactory beanFactory = applicationContext;
            CachePurchaseDefinitionService purchaseDefinitionService = beanFactory.getBean(CachePurchaseDefinitionService.class);
            purchaseDefinitionService.setDelegate(this.purchaseDefinitionService);
            this.purchaseDefinitionService = purchaseDefinitionService;
        }
    }

    @Override
    public String configCode() {
        // synchronized
        final StringBuffer purchase = new StringBuffer();
        ArrayList<Runnable> taskList = new ArrayList<>(TASK);
        taskList.add(() -> purchase.insert(PURCHASE_DEFINITION.getStart(), purchaseDefinitionService.configCode()));
        if (CheckRequestContext.getInstance().getOrderType() == PURCHASE_ORDER) {
            taskList.add(() -> purchase.insert(PURCHASE_AUDIT.getStart(), purchaseDefinitionService.configCode()));
        } else {
            taskList.add(() -> purchase.insert(PURCHASE_AUDIT.getStart(), INIT_CODE));
        }

        SimpleThreadPool.executeRunnable(CREATE_ORDER, taskList.toArray(new Runnable[taskList.size()]));
        return purchase.toString();
    }

}
