package com.kevin.tool.order.sendpay.check;

import com.kevin.tool.order.sendpay.CodeApplicationContextImpl;
import com.kevin.tool.order.sendpay.Stage;
import com.kevin.tool.order.sendpay.generate.param.CodeParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.context.ApplicationContext;
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
@Slf4j
@SuppressWarnings({"unused", "JavadocReference"})
@Component
@ConditionalOnMissingBean(value = {CodeApplicationContextImpl.class}, search = SearchStrategy.CURRENT)
public class CheckCodeContext extends AbstractSegmentContext implements BeanFactoryAware, BeanNameAware, InitializingBean {

    /**
     *  当前注册的Bean名称
     */
    private static String BEAN_NAME;

    /**
     *  所有的服务列表
     */
    protected static Set<CheckService> checkServiceSet = ConcurrentHashMap.newKeySet();

    /**
     *  配置每个订单节点对应可能需要检查的服务，而服务本身有先后顺序，跳过不检查的服务，本身类似一个责任链
     * @see Collections.UnmodifiableMap
     */
    private static Map<Stage, Entry> CONFIG_INDEX;

    /**
     *  节点服务（责任）链
     * @see Collections.UnmodifiableMap
     */
    private static Map<Stage, List<CheckService>> CONFIG_SERVICE;

    /**
     * Bean工厂
     */
    protected BeanFactory beanFactory;

    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() {
        Stage[] states = Stage.values();
        // 初始化责任链的链条
        Map<Stage, Entry> configIndex = new HashMap<>(states.length / 4 * 3 + 1);
        Map<Stage, List<CheckService>> configService = new HashMap<>(states.length / 4 * 3 + 1);

        for (Stage stage : states) {
            StateConfig stateConfig = StateConfig.getStatus(stage);
            if (stateConfig.getStart() > 0) {
                configIndex.put(stage, new Entry(stateConfig.getStart(), stateConfig.getEnd()));
            }

            if (stateConfig.getCheckList().isEmpty()) {
                continue;
            }
            List<CheckService> checkServices = new ArrayList<>();
            stateConfig.getCheckList().forEach(clazz -> {
                CheckService bean = beanFactory.getBean(clazz);
                checkServices.add(bean);
                checkServiceSet.add(bean);
            });
            configService.put(stage, checkServices);
        }

        CONFIG_INDEX = Collections.unmodifiableMap(configIndex);
        CONFIG_SERVICE = Collections.unmodifiableMap(configService);
    }

    /**
     *  检查每个节点是否验证通过
     * @param codeParam 请求参数
     * @param stage 节点
     * @return 是否检查通过 不会返回{@code null}
     */
    @Override
    public Boolean check(CodeParam codeParam, Stage stage) {
        CheckRequestContext.getInstance().set(new RequestContextParam(codeParam, stage, CONFIG_INDEX.get(stage)));
        try {
            String segmentCode = super.getSegment();
            Boolean[] checks = CodeUtil.checkChain(segmentCode);
            int i = 0;
            List<CheckService> checkServices = CONFIG_SERVICE.get(stage);
            for (CheckService checkService : checkServices) {
                if (checks[i] && !checkService.isCheck()) {
                    return false;
                }
                i++;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
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
    public void setBeanName(String name) {
        BEAN_NAME = name;
        log.info("getBean " + CheckCodeContext.class.getName() + ", bean name is " + BEAN_NAME);
    }

    @Override
    protected Entry getStateConfig(Stage stage) {
        return CONFIG_INDEX.get(stage);
    }
}
