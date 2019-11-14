package com.kevin.tool.spring.ioc.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
@EnableScheduling
@ImportResource(locations= {"classpath:spring-bean.xml"})
public class TestComponent implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Scheduled(fixedRate = 3000, initialDelay = 3000)
    public void destroy() {
        SingletonBeanLifeCycle bean = applicationContext.getBean(SingletonBeanLifeCycle.class);
        System.out.println("8）、使用中bean当前的testName=" + bean.getTestName());
        ((AbstractApplicationContext) applicationContext).close();
    }
}
