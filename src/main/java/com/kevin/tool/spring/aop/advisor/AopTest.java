package com.kevin.tool.spring.aop.advisor;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.aop.framework.adapter.AfterReturningAdviceInterceptor;
import org.springframework.aop.framework.adapter.ThrowsAdviceInterceptor;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class AopTest extends AfterReturningAdviceInterceptor implements MethodBeforeAdvice,
        MethodInterceptor,
        org.aopalliance.intercept.MethodInterceptor,
        AfterReturningAdvice,
        ThrowsAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {

    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return null;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return null;
    }

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {

    }

    public AopTest(AfterReturningAdvice advice) {
        super(advice);
    }


}
