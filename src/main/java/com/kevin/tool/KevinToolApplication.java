package com.kevin.tool;

import com.github.dreamroute.locker.interceptor.OptimisticLocker;
import com.kevin.tool.async.impl.CreateOrderImpl;
import com.kevin.tool.mybatis.plugins.EnableOptimisticLock;
import com.kevin.tool.order.OrderEvent;
import com.kevin.tool.order.OrderState;
import com.kevin.tool.timeconsume.EnableTimeConsume;
import com.mysql.cj.jdbc.Driver;
import com.mysql.cj.jdbc.NonRegisteringDriver;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.statemachine.StateMachine;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;


/**
 *    启动类
 * @author lihongmin
 * @date 2019/10/25 13:45
 */
@RestController
@EnableTimeConsume
@EnableAsync
@ComponentScan("com.kevin.tool")
@MapperScan
@EnableTransactionManagement
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableScheduling
@EnableJpaRepositories
@EnableOptimisticLock
public class KevinToolApplication implements BeanFactoryAware, CommandLineRunner {

    private BeanFactory beanFactory;

    @Autowired
    private StateMachine<OrderState, OrderEvent> stateMachine;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

//    @Scheduled(fixedRate = 3000)
    public void get() {
        CreateOrderImpl bean = (CreateOrderImpl)beanFactory.getBean("createOrderImpl");
//        System.out.println(bean);
    }

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(KevinToolApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.SERVLET);
        springApplication.run(args);
    }

    @Bean
    public OptimisticLocker locker() {
        OptimisticLocker locker = new OptimisticLocker();
        // 不设置versionColumn，默认为version
        Properties props = new Properties();
        props.setProperty("versionColumn", "version");
        locker.setProperties(props);
        return locker;
    }

    @Override
    public void run(String... args) {

        Collections.synchronizedList(new ArrayList<String>());
        boolean isSuccess = stateMachine.sendEvent(OrderEvent.EVENT1);
        boolean isSuccess2 = stateMachine.sendEvent(OrderEvent.EVENT2);
    }



}



