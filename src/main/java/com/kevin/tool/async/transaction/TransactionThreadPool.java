package com.kevin.tool.async.transaction;

import com.kevin.tool.async.SimpleThreadPool;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration;
import org.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/**
 *  事务线程
 * @author lihongmin
 * @date 2019/11/27 8:56
 * @since 2.1.8
 */
@Component
public class TransactionThreadPool extends SimpleThreadPool implements BeanFactoryAware {

    private BeanFactory beanFactory;

    public static final InheritableThreadLocal threadLocal = new InheritableThreadLocal();

    private Map<Thread, Boolean> map = new ConcurrentHashMap<>();

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public BeanFactoryTransactionAttributeSourceAdvisor get() {
        return beanFactory.getBean(BeanFactoryTransactionAttributeSourceAdvisor.class);
    }

    public ProxyTransactionManagementConfiguration getConfig() {
        return beanFactory.getBean(ProxyTransactionManagementConfiguration.class);
    }

    public Boolean hasThread(Thread thread) {
        if (thread == null) {
            return false;
        }
        return map.get(thread);
    }

    /**
     *
     * @param threadPoolEnum
     * @param callableList
     * @param <T>
     * @return
     */
    public <T> List<Future<T>> invokeTransactionAll(ThreadPoolEnum threadPoolEnum, List<Callable<T>> callableList) {
//        TransactionInterceptor interceptor = (TransactionInterceptor)get().getAdvice();
//        PlatformTransactionManager transactionManager = interceptor.getTransactionManager();

        /*EntityManagerHolder emHolder = (EntityManagerHolder)TransactionSynchronizationManager.getResource(getManager().getEntityManagerFactory());
        if (emHolder != null) {
            map.put(Thread.currentThread(), Boolean.TRUE);
        }*/
        List<Future<T>> futures = executeAll(threadPoolEnum, callableList);
        map.remove(Thread.currentThread());
        return futures;
    }
}
