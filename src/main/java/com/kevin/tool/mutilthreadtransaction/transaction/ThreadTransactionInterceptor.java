package com.kevin.tool.mutilthreadtransaction.transaction;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.InfrastructureAdvisorAutoProxyCreator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.lang.reflect.Method;

import static com.kevin.tool.mutilthreadtransaction.transaction.TransactionThreadPool.hasTransaction;

/**
 *  父子线程的事务拦截器
 * @author lihongmin
 * @date 2019/11/29 9:26
 * @since 2.1.8
 */
@Component
public class ThreadTransactionInterceptor extends TransactionInterceptor {

    /**
     *  重写父类
     * @see TransactionAspectSupport#afterPropertiesSet() 重写父类方法，防止抛异常
     */
    @Override
    public void afterPropertiesSet() {

    }

    /**
     *  是否在事务中执行该方法
     *
     * @param method 当前方法
     * @param targetClass 当前类
     * @param invocation 回调方法
     * @return 执行结果
     * @throws Throwable 执行异常直接抛出去
     * @see InfrastructureAdvisorAutoProxyCreator#postProcessAfterInitialization(Object, String)
     * @see AbstractAdvisorAutoProxyCreator#getAdvicesAndAdvisorsForBean(Class, String, TargetSource)
     * @see AbstractAutoProxyCreator#createProxy(Class, String, Object[], TargetSource)
     */
    @Override
    protected Object invokeWithinTransaction(Method method, Class<?> targetClass, InvocationCallback invocation) throws Throwable {
        // 父线程中开启事务，直接执行当前的回调方法
        if (hasTransaction()) {
            return invocation.proceedWithInvocation();
        }
        return super.invokeWithinTransaction(method, targetClass, invocation);
    }

}
