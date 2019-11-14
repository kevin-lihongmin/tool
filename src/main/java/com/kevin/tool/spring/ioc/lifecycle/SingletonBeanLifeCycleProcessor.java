package com.kevin.tool.spring.ioc.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 *  测试回调-1
 * @author kevin
 * @date 2019/11/14 15:27
 * @since 1.0.0
 */
@Component
@Order(1)
public class SingletonBeanLifeCycleProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("kevin".equals(beanName)) {
            System.out.println("4-1）、Processor-1-Before： beanName=" + beanName + "，bean Class<?>=" + bean.getClass() + "的bean调用getBean方法,\n" +
                    "-----BeanPostProcessor.postProcessBeforeInitialization回调了SingletonBeanLifeCycleProcessor！");
            ((SingletonBeanLifeCycle)bean).setTestName("kevin-2");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if ("kevin".equals(beanName)) {
            System.out.println("7-1）、Processor-1-After： beanName=" + beanName + "，bean Class<?>=" + bean.getClass() + "的bean调用getBean方法, \n" +
                    "-----BeanPostProcessor.postProcessAfterInitialization回调了SingletonBeanLifeCycleProcessor！");
        }
        return bean;
    }
}
