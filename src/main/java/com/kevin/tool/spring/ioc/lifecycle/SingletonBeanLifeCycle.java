package com.kevin.tool.spring.ioc.lifecycle;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

/**
 *  单利bean的生命周期
 *
 * @author kevin
 * @date 2019/11/14 15:07
 * @since 1.0.0
 */
@Component
@Data
@ToString
public class SingletonBeanLifeCycle implements BeanNameAware, BeanFactoryAware,
        ApplicationContextAware, InitializingBean, DisposableBean {

    private String testName;

    @Override
    public void setBeanName(String name) {
        System.out.println("1）、BeanNameAware.setBeanName方法回调：name=" + name);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("2）、BeanFactoryAware.setBeanFactory方法回调：beanFactory=" + beanFactory);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("3）、ApplicationContextAware.setApplicationContext方法回调：applicationContext=" + applicationContext);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("5）、InitializingBean.afterPropertiesSet方法回调了！");
    }

    public void initMedthod() {
        System.out.println("6）、initMethod方法被调用了！");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("9）DisposableBean.destroy方法回调了！");
    }

    public void destroyMedthod() {
        System.out.println("10）destroyMedthod方法被调用了！");
    }

}
