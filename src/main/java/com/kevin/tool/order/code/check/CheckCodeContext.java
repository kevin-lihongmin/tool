package com.kevin.tool.order.code.check;

import com.kevin.tool.order.code.CodeApplicationContext;
import com.kevin.tool.order.code.SegmentState;
import com.kevin.tool.order.code.check.marker.DefaultMarkerFlagService;
import com.kevin.tool.order.code.check.marker.MarkerFlagService;
import com.kevin.tool.order.code.generate.param.CodeParam;
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

/**
 *  订单流程每个节点，根据配置和订单码，检查是否通过
 *
 * @author lihongmin
 * @date 2020/6/30 16:40
 * @since 1.0.0
 */
@SuppressWarnings("unused")
@Component
@ConditionalOnMissingBean(value = {CodeApplicationContext.class}, search = SearchStrategy.CURRENT)
public class CheckCodeContext extends AbstractSegmentContext implements MarkerFlagService, BeanFactoryAware, BeanNameAware, /*ApplicationContextAware,*/ InitializingBean {

    /**
     *  当前注册的Bean名称
     */
    private static String BEAN_NAME;

    /**
     *  所有的服务列表
     */
    private static Set<CheckService> checkServiceSet;

    /**
     *  配置每个订单节点对应可能需要检查的服务，而服务本身有先后顺序，跳过不检查的服务，本身类似一个责任链
     */
    private static Map<SegmentState, Entry> CONFIG_INDEX;

    /**
     *  节点服务（责任）链
     */
    private static Map<SegmentState, List<CheckService>> CONFIG_SERVICE;

    /**
     * Bean工厂
     */
    private BeanFactory beanFactory;

    private ApplicationContext applicationContext;

    private static MarkerFlagService markerFlagService = new DefaultMarkerFlagService();

    @Override
    public void afterPropertiesSet() {
        SegmentState[] states = SegmentState.values();
        // 初始化责任链的链条
        Map<SegmentState, Entry> configIndex = new HashMap<>(states.length / 4 * 3 + 1);
        Map<SegmentState, List<CheckService>> configService = new HashMap<>(states.length / 4 * 3 + 1);

        for (SegmentState segmentState : states) {
            StateConfig stateConfig = StateConfig.getStatus(segmentState);
            configIndex.put(segmentState, new Entry(stateConfig.getStart(), stateConfig.getEnd()));

            List<CheckService> checkServices = new ArrayList<>();
            stateConfig.getCheckList().forEach(clazz -> {
                CheckService bean = beanFactory.getBean(clazz);
                checkServices.add(bean);
                checkServiceSet.add(bean);
            });
            configService.put(segmentState, checkServices);
        }

        CONFIG_INDEX = Collections.unmodifiableMap(configIndex);
        CONFIG_SERVICE = Collections.unmodifiableMap(configService);
    }

    /**
     *  检查每个节点是否验证通过
     * @param codeParam 请求参数
     * @param segmentState 节点
     * @return 是否检查通过 不会返回{@code null}
     */
    @Override
    public Boolean check(CodeParam codeParam, SegmentState segmentState) {
        CheckRequestContext.getInstance().set(new RequestContextParam(codeParam, segmentState, CONFIG_INDEX.get(segmentState)));
        try {
            String segmentCode = super.getSegment();
            Boolean[] checks = CodeUtil.checkChain(segmentCode);
            int i = 0;
            List<CheckService> checkServices = CONFIG_SERVICE.get(segmentState);
            for (CheckService checkService : checkServices) {
                if (checks[i] && !checkService.isCheck()) {
                    return false;
                }
                i++;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            CheckRequestContext.getInstance().remove();
        }
        return true;
    }

    @Override
    public Boolean isAutoOrder(String code) {
        return markerFlagService.isAutoOrder(code);
    }

    @Override
    public Boolean isTms(String code) {
        return markerFlagService.isTms(code);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        BEAN_NAME = name;
    }

    /*@Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }*/

    @Override
    protected Entry getStateConfig(SegmentState segmentState) {
        return CONFIG_INDEX.get(segmentState);
    }
}
