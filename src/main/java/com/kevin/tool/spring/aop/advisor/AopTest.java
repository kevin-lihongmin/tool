package com.kevin.tool.spring.aop.advisor;

import com.kevin.tool.spring.aop.DemoService;
import com.kevin.tool.spring.aop.DemoServiceImpl;
import org.springframework.aop.framework.ProxyFactory;

/**
 *  Aop代理测试
 *
 * @author lihongmin
 * @date 2019/11/20 13:39
 * @since 2.1.8
 */
public class AopTest {

    public static void main(String[] args) {
        // 自动代理
        ProxyFactory proxyFactory = new ProxyFactory();
        // 目标对象(DemoServiceImpl)
        proxyFactory.setTarget(new DemoServiceImpl());
        // 增强(AopAdvice)
        AopAdvice aopAdvice = new AopAdvice();
//        proxyFactory.addAdvice();
        // 切点(DemoStaticPointcutAdvisor)
        DemoStaticPointcutAdvisor advisor = new DemoStaticPointcutAdvisor();
        advisor.setAdvice(aopAdvice);
        proxyFactory.addAdvisor(advisor);

        // 从代理工厂 获取 切面
        DemoService demoService = (DemoService)proxyFactory.getProxy();
        System.out.println("----------------------------");
        demoService.doSomething("kevin!");
        System.out.println("----------------------------");
        demoService.doOther("kevin!");
        System.out.println("----------------------------");
    }
}
