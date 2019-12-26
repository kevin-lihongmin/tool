package com.kevin.tool.mutilthreadtransaction.transaction;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration;
import org.springframework.transaction.config.TransactionManagementConfigUtils;
import org.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static com.kevin.tool.mutilthreadtransaction.transaction.TransactionThreadPool.THREAD_POOL_FLAG;

/**
 *  替换Spring容器中的{@link TransactionInterceptor} 为自定义的{@link ThreadTransactionInterceptor}
 *
 * @author lihongmin
 * @date 2019/11/29 9:26
 * @since 2.1.8
 * @see AdviceMode#PROXY
 * @see EnableTransactionManagement#mode() 当前只支持 {@link AdviceMode#PROXY}
 */
@Slf4j
@Component
public class ReplaceTransactionInterceptor implements BeanFactoryPostProcessor {

    /**
     *  支持的事务启动类型
     */
    public static void error() {
        String errorInfo = "多线程事务不支持AdviceMode.ASPECTJ";
        log.error(errorInfo);
        throw new RuntimeException(errorInfo);
    }

    /**
     *  事务切面默认{@code Bean} 名称
     */
    private static String DEFAULT_TRANSACTION_ADVISOR = TransactionManagementConfigUtils.TRANSACTION_ADVISOR_BEAN_NAME;

    private static final Set<String> IGNORE_REGIST = Collections.unmodifiableSet(Sets.newHashSet(DEFAULT_TRANSACTION_ADVISOR,
            "transactionInterceptor", "threadTransactionInterceptor", "org.springframework.aop.config.internalAutoProxyCreator"));


    static {
        Class<? extends Class> clazz = ProxyTransactionManagementConfiguration.class.getClass();
        if (THREAD_POOL_FLAG) {
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
                System.out.println("没有拿到");
                /*e.printStackTrace();*/
            }
        }
    }

    /**
     *  替换{@link TransactionInterceptor}
     * @param beanFactory Bean工厂
     * @throws BeansException 创建异常
     * @see AbstractApplicationContext#refresh()
     * @see AbstractApplicationContext#obtainFreshBeanFactory() 之<b>后<b/>执行
     * @see AbstractApplicationContext#invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory) 之<b>后<b/>执行
     * @see AbstractApplicationContext#finishBeanFactoryInitialization(ConfigurableListableBeanFactory) 之<b>前<b/>执行
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        if (THREAD_POOL_FLAG) {
            // 获取ProxyTransactionManagementConfiguration初始化的三个Bean
            BeanFactoryTransactionAttributeSourceAdvisor bean = null;
            try {
                bean = (BeanFactoryTransactionAttributeSourceAdvisor) beanFactory.getBean(DEFAULT_TRANSACTION_ADVISOR);
            } catch (NoSuchBeanDefinitionException e) {
                error();
            }
            TransactionInterceptor interceptor = (TransactionInterceptor) beanFactory.getBean("transactionInterceptor");
            TransactionAttributeSource transactionAttributeSource = (TransactionAttributeSource) beanFactory.getBean("transactionAttributeSource");
            // 获取支持线程的TransactionInterceptor
            ThreadTransactionInterceptor threadTransactionInterceptor = (ThreadTransactionInterceptor) beanFactory.getBean("threadTransactionInterceptor");

            // 属性设置
            threadTransactionInterceptor.setTransactionAttributeSource(transactionAttributeSource);
            TransactionManager transactionManager = interceptor.getTransactionManager();
            if (transactionManager != null) {
                threadTransactionInterceptor.setTransactionManager(transactionManager);
            }
            bean.setAdvice(threadTransactionInterceptor);
            // 移除transactionInterceptor的BeanDefinition
            DefaultListableBeanFactory factory = (DefaultListableBeanFactory) beanFactory;
            factory.removeBeanDefinition("threadTransactionInterceptor");

            String[] singletonNames = beanFactory.getSingletonNames();
            Map<String, Object> map = new LinkedHashMap<>();
            for (String name : singletonNames) {
                map.put(name, beanFactory.getBean(name));
            }

            beanFactory.destroySingletons();

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (IGNORE_REGIST.contains(entry.getKey())) {
                    continue;
                }
                beanFactory.registerSingleton(entry.getKey(), entry.getValue());
            }
            // 重新提前注册Bean
            beanFactory.registerSingleton("transactionInterceptor", threadTransactionInterceptor);
            beanFactory.registerSingleton(DEFAULT_TRANSACTION_ADVISOR, bean);

        }
    }

}
