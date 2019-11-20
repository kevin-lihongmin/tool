package com.kevin.tool.spring.aop.advisor;

import com.kevin.tool.spring.aop.DemoService;
import com.kevin.tool.spring.aop.DemoServiceImpl;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.AopInvocationException;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 *  Aop增强
 * @author lihongmin
 * @date 2019/11/20 13:54
 * @since 2.1.8
 */
public class AopAdvice /*extends AopInvocationException*/ implements MethodBeforeAdvice,
        MethodInterceptor,
        org.aopalliance.intercept.MethodInterceptor,
        AfterReturningAdvice{

    /**
     *  前置Aop
     * @param method 代理方法
     * @param args 方法参数
     * @param target 代理对象
     * @throws Throwable
     * @see MethodBeforeAdvice
     */
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("before 执行了！");
    }

    /**
     *  cglib代理特殊处理
     * @param o 代理对象
     * @param method 方法本身
     * @param objects 方法参数
     * @param methodProxy 代理对象
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("实现cglib调用！");

        Object invoke = methodProxy.invoke(o, objects);
        return invoke;
    }

    /**
     *  Around Aop
     * @param invocation 方法代理
     * @return 执行返回
     * @throws Throwable 异常
     * @see org.aopalliance.intercept.MethodInterceptor
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("Around 前执行了！");

        Object proceed = invocation.proceed();

        System.out.println("Around 后执行了！");
        return proceed;
    }

    /**
     *  后置 Aop
     * @param returnValue 返回值
     * @param method 方法
     * @param args 参数
     * @param target 代理对象
     * @throws Throwable 异常
     */
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("afterReturning 执行了");
    }
}
    /**
     *  异常处理
     * @param method 方法
     * @param args 参数
     * @param target 代理对象
     * @param e 异常
     * @see org.aspectj.lang.annotation.AfterThrowing
     */
    /*public void afterThrowing(Method method, Object[] args, Object target, Exception e) {
        System.out.println("发生了异常！");
        if (!"doSomething".equals(method.getName())) {
            System.out.println(e);
        }
    }

    public AopAdvice(String msg) {
        super(msg);
    }

    public AopAdvice(String msg, Throwable cause) {
        super(msg, cause);
        System.out.println(msg);
    }*/

