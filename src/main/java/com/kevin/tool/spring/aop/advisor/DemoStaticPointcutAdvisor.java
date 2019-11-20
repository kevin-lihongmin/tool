package com.kevin.tool.spring.aop.advisor;

import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;

import java.lang.reflect.Method;

/**
 *  切点
 * @author lihongmin
 * @date 2019/11/20 13:25
 */
public class DemoStaticPointcutAdvisor extends StaticMethodMatcherPointcutAdvisor {

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        System.out.println("调用了是否匹配切点！");
        return "doSomething".equals(method.getName());
    }
}
