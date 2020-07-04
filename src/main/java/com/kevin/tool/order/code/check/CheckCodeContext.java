package com.kevin.tool.order.code.check;

import com.kevin.tool.order.code.CodeApplicationContext;
import com.kevin.tool.order.code.CodeUtil;
import com.kevin.tool.order.code.check.impl.UserCheckService;
import com.kevin.tool.order.code.generate.param.CodeParam;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  订单流程每个节点，根据配置和订单码，检查是否通过
 *
 * @author lihongmin
 * @date 2020/6/30 16:40
 * @since 1.0.0
 */
@Component
@ConditionalOnMissingBean(value = {CodeApplicationContext.class}, search = SearchStrategy.CURRENT)
public class CheckCodeContext extends SegmentContext implements BeanFactoryAware, ApplicationContextAware, InitializingBean {

    private static Set<CheckService> set;

    /**
     *  配置每个订单节点对应可能需要检查的服务，而服务本身有先后顺序，跳过不检查的服务，本身类似一个责任链
     */
    private static Map<Segment.STATUS, LinkedHashSet<Class<? extends CheckService>>> CONFIG_CLAZZ_SET;

    private static Map<Segment.STATUS, LinkedHashSet<CheckService>> CONFIG_SERVICE_SET;

    /**
     * Bean工厂
     */
    private BeanFactory beanFactory;

    private ApplicationContext applicationContext;

    /*static {
        LinkedHashSet<Class<? extends CheckService>> services = new LinkedHashSet<>();
        services.add(UserCheckService.class);

        // gc services
        CLAZZ_SET = services;
    }*/

    @Override
    public void afterPropertiesSet() throws Exception {
        // 初始化责任链的链条
        CONFIG_CLAZZ_SET = new HashMap<>(Segment.STATUS.values().length / 4 * 3 + 1);

        LinkedHashSet<Class<? extends CheckService>> services = new LinkedHashSet<>();
        services.add(UserCheckService.class);

        // gc services
        CONFIG_CLAZZ_SET.put(Segment.STATUS.CREATE_ORDER, services);

        CONFIG_SERVICE_SET = new ConcurrentHashMap<>();


//        applicationContext.getBeanNamesForType();
        /*TreeSet<CheckService> chain = new TreeSet<>();
        CLAZZ_SET.forEach(service -> {
            chain.add(beanFactory.getBean(service));
        });
        set = Collections.unmodifiableSet(chain);*/
    }

    /**
     *  检查每个节点是否验证通过
     * @param codeParam 请求参数
     * @param status 节点
     * @return 是否检查通过 不会返回{@code null}
     */
    @Override
    public Boolean check(CodeParam codeParam, Segment.STATUS status) {
        CheckRequestContext.getInstance().set(new RequestContextParam(codeParam, status));
        try {
            String segmentCode = super.getSegment();
            Boolean[] checks = CodeUtil.checkChain(segmentCode);
            int i = 0;
            Boolean result = true;
            LinkedHashSet<Class<? extends CheckService>> classes = CONFIG_CLAZZ_SET.get(status);
            for (Class<? extends CheckService> clazz : classes) {
                if (checks[i]) {
                    Boolean checked = beanFactory.getBean(clazz).isCheck();
                    if (!checked) {
                        return false;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            CheckRequestContext.getInstance().remove();
        }
        return true;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
