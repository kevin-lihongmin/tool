package com.kevin.tool;

import com.kevin.tool.async.impl.CreateOrderImpl;
import com.kevin.tool.order.OrderEvent;
import com.kevin.tool.order.OrderState;
import com.kevin.tool.order.code.CodeApplicationContext;
import com.kevin.tool.timeconsume.EnableTimeConsume;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.statemachine.StateMachine;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;


/**
 *    启动类
 * @author lihongmin
 * @date 2019/10/25 13:45
 */
@RestController
@EnableTimeConsume
@EnableAsync
@ComponentScan("com.kevin.tool")
@EnableTransactionManagement
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableScheduling
@EnableJpaRepositories
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

    @Override
    public void run(String... args) {
        boolean isSuccess = stateMachine.sendEvent(OrderEvent.EVENT1);
        boolean isSuccess2 = stateMachine.sendEvent(OrderEvent.EVENT2);
    }
    
    public void mybatis() {
        //前三步都相同
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = factory.openSession();
        //这里不再调用SqlSession 的api，而是获得了接口对象，调用接口中的方法。
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
//        List<User> list = mapper.getUserByName("tom");
    }

}



