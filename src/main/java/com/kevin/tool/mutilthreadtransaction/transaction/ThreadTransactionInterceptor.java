package com.kevin.tool.mutilthreadtransaction.transaction;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.InfrastructureAdvisorAutoProxyCreator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.lang.reflect.Method;
import java.util.Properties;

import static com.kevin.tool.mutilthreadtransaction.transaction.TransactionThreadPool.hasTransaction;

/**
 *  父子线程的事务拦截器
 * @author lihongmin
 * @date 2019/11/29 9:26
 * @since 2.1.8
 */
@Component
public class ThreadTransactionInterceptor extends TransactionInterceptor {

    public ThreadTransactionInterceptor() {
    }

    public ThreadTransactionInterceptor(PlatformTransactionManager ptm, Properties attributes) {
        super(ptm, attributes);
    }

    public ThreadTransactionInterceptor(PlatformTransactionManager ptm, TransactionAttributeSource tas) {
        super(ptm, tas);
    }

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
        Boolean hasTransaction = hasTransaction();
        if (hasTransaction) {
            return invocation.proceedWithInvocation();
        }

        TransactionThreadPool.INHERITABLE_THREAD.set(Boolean.TRUE);
        Object result = super.invokeWithinTransaction(method, targetClass, invocation);
        TransactionThreadPool.INHERITABLE_THREAD.set(Boolean.FALSE);
        return result;
    }

}
