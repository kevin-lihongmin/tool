package com.kevin.tool.async.transaction;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration;
import org.springframework.transaction.config.TransactionManagementConfigUtils;
import org.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

@Component
public class ReplaceTransactionInterceptor implements BeanFactoryPostProcessor {

    /**
     *  事务切面默认{@code Bean} 名称
     */
    private static String DEFAULT_TRANSACTION_ADVISOR = TransactionManagementConfigUtils.TRANSACTION_ADVISOR_BEAN_NAME;

//    private static String

    static {
        Class<ProxyTransactionManagementConfiguration> clazz = ProxyTransactionManagementConfiguration.class;
        try {
            Method transactionAdvisor = clazz.getMethod("transactionAdvisor", TransactionAttributeSource.class, TransactionInterceptor.class);
            Bean annotation = transactionAdvisor.getAnnotation(Bean.class);
            String[] name = annotation.name();
            if (name != null && name.length > 0) {
                DEFAULT_TRANSACTION_ADVISOR = name[0];
            }
            String[] value = annotation.value();
            if (value != null && value.length > 0) {
                DEFAULT_TRANSACTION_ADVISOR = value[0];
            }


        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String str : beanDefinitionNames) {
            System.out.println(str);
        }

        BeanDefinition advisor = beanFactory.getBeanDefinition(DEFAULT_TRANSACTION_ADVISOR);
        MutablePropertyValues propertyValues = advisor.getPropertyValues();
        BeanDefinition transactionInterceptor = beanFactory.getBeanDefinition("transactionInterceptor");
//        BeanDefinition threadTransactionInterceptor = beanFactory.getBeanDefinition("threadTransactionInterceptor");

        BeanFactoryTransactionAttributeSourceAdvisor bean = (BeanFactoryTransactionAttributeSourceAdvisor)beanFactory.getBean(DEFAULT_TRANSACTION_ADVISOR);
        TransactionInterceptor interceptor = (TransactionInterceptor)beanFactory.getBean("transactionInterceptor");
        TransactionAttributeSource transactionAttributeSource = (TransactionAttributeSource)beanFactory.getBean("transactionAttributeSource");

        ThreadTransactionInterceptor threadTransactionInterceptor = (ThreadTransactionInterceptor)beanFactory.getBean("threadTransactionInterceptor");

        threadTransactionInterceptor.setTransactionAttributeSource(transactionAttributeSource);
        /*PlatformTransactionManager transactionManager = config.transactionInterceptor().getTransactionManager();
        if (transactionManager != null) {
            setTransactionManager(transactionManager);
        }*/
        ((BeanFactoryTransactionAttributeSourceAdvisor)bean).setAdvice(threadTransactionInterceptor);

        System.out.println(bean);
    }
}
