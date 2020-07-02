package com.kevin.tool.order.code.check;

import com.kevin.tool.order.code.CodeUtil;
import com.kevin.tool.order.code.generate.param.CodeParam;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 *  订单流程每个节点，根据配置和订单码，检查是否通过
 *
 * @author lihongmin
 * @date 2020/6/30 16:40
 * @since 1.0.0
 */
@Component
public class CheckCodeContext extends SegmentContext implements BeanFactoryAware, InitializingBean {

    private static Set<CheckService> set;

    /**
     *  配置每个订单节点对应可能需要检查的服务，而服务本身有先后顺序，跳过不检查的服务，本身类似一个责任链
     */
    private static Map<STATUS, LinkedHashSet<Class<? extends CheckService>>> CONFIG_CLAZZ_SET;

    /**
     * Bean工厂
     */
    private BeanFactory beanFactory;

    /*static {
        LinkedHashSet<Class<? extends CheckService>> services = new LinkedHashSet<>();
        services.add(UserCheckService.class);

        // gc services
        CLAZZ_SET = services;
    }*/

    @Override
    public void afterPropertiesSet() throws Exception {
        CONFIG_CLAZZ_SET = new HashMap<>(STATUS.values().length);

        LinkedHashSet<Class<? extends CheckService>> services = new LinkedHashSet<>();
        services.add(UserCheckService.class);

        // gc services
        CONFIG_CLAZZ_SET.put(STATUS.CREATE_ORDER, services);
        /*TreeSet<CheckService> chain = new TreeSet<>();
        CLAZZ_SET.forEach(service -> {
            chain.add(beanFactory.getBean(service));
        });
        set = Collections.unmodifiableSet(chain);*/
    }
    
    public Boolean check(CodeParam codeParam, STATUS status) {
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
}
