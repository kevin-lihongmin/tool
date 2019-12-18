package com.kevin.tool;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor;
import org.springframework.web.bind.annotation.RestController;


/**
 *    启动类
 * @author lihongmin
 * @date 2019/10/25 13:45
 */
@RestController
//@EnableTimeConsume
@EnableAsync
@ComponentScan("com.kevin.tool")
@EnableTransactionManagement
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableScheduling
public class KevinToolApplication implements BeanFactoryAware {

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Scheduled(fixedRate = 3000)
    public void get() {
        BeanFactoryTransactionAttributeSourceAdvisor bean = beanFactory.getBean(BeanFactoryTransactionAttributeSourceAdvisor.class);
        System.out.println(bean);
    }

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(KevinToolApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.SERVLET);
        springApplication.run(args);
    }
}


