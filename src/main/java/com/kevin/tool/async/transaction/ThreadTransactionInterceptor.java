package com.kevin.tool.async.transaction;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration;
import org.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.lang.reflect.Method;
import java.util.Set;

@Component
public class ThreadTransactionInterceptor extends TransactionInterceptor implements BeanPostProcessor {

    @Autowired
    private TransactionThreadPool transactionThreadPool;

    @Override
    public void afterPropertiesSet() {

    }

    @Override
    protected Object invokeWithinTransaction(Method method, Class<?> targetClass, InvocationCallback invocation) throws Throwable {
        if (hasTransaction()) {
            return invocation.proceedWithInvocation();
        }
        return super.invokeWithinTransaction(method, targetClass, invocation);
    }

    private Boolean hasTransaction() {
        Object tag = TransactionThreadPool.threadLocal.get();
        if (tag == null) {
            return false;
        }
        return (Boolean)tag;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        /*boolean name = BeanFactoryTransactionAttributeSourceAdvisor.class.getSimpleName().equals(beanName);
        boolean assignableFrom = BeanFactoryTransactionAttributeSourceAdvisor.class.isAssignableFrom(bean.getClass());
        if (name && assignableFrom) {
            ProxyTransactionManagementConfiguration config = transactionThreadPool.getConfig();
            setTransactionAttributeSource(config.transactionAttributeSource());
            PlatformTransactionManager transactionManager = config.transactionInterceptor().getTransactionManager();
            if (transactionManager != null) {
                setTransactionManager(transactionManager);
            }
            ((BeanFactoryTransactionAttributeSourceAdvisor)bean).setAdvice(this);
        }*/
        return bean;
    }

}
