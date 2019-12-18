package com.kevin.tool.mutilthreadtransaction.transaction;

import com.kevin.tool.async.SimpleThreadPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import static com.kevin.tool.mutilthreadtransaction.transaction.ReplaceTransactionInterceptor.error;

/**
 *  事务线程
 *  
 * @author lihongmin
 * @date 2019/12/18 15:48
 * @since 1.0.0
 */
@Slf4j
@Component
public class TransactionThreadPool extends SimpleThreadPool implements BeanFactoryAware, ImportBeanDefinitionRegistrar {

    /**
     *  多线程使用开关
     */
    public static volatile Boolean THREAD_POOL_FLAG = true;

    private JpaTransactionManager jpaTransactionManager;

    /**
     *  父线程开启事务的状态
     */
    public static final InheritableThreadLocal<Boolean> INHERITABLE_THREAD = new InheritableThreadLocal();

    /**
     *  父类是否已经开启线程
     * @return 是否开启
     */
    public static Boolean hasTransaction() {
        Boolean isOpen = INHERITABLE_THREAD.get();
        return Boolean.TRUE.equals(isOpen);
    }

    /**
     *  可以执行策略
     *  事务管理器，当前直接获取jpa的事务管理器
     * @param beanFactory Bean工厂
     * @throws BeansException Bean创建异常
     *
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (THREAD_POOL_FLAG) {
            jpaTransactionManager = (JpaTransactionManager) beanFactory.getBean("transactionManager");
        }
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 开启事务则检查AdviceMode
        if (THREAD_POOL_FLAG) {
            Set<String> annTypes = importingClassMetadata.getAnnotationTypes();
            Iterator var5 = annTypes.iterator();

            while (var5.hasNext()) {
                String annType = (String) var5.next();
                AnnotationAttributes candidate = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(annType, false));
                if (candidate != null) {
                    Object mode = candidate.get("mode");
                    Object proxyTargetClass = candidate.get("proxyTargetClass");
                    if (mode != null && proxyTargetClass != null && AdviceMode.class == mode.getClass() && Boolean.class == proxyTargetClass.getClass()) {
                        if (mode != AdviceMode.PROXY) {
                            error();
                        }
                    }
                }
            }
        }
    }

    /**
     *  调用该方法时，不能使用 {@link CrudRepository} 的实现类直接保存，比如最终实现为{@link SimpleJpaRepository}
     *  使用了{@link JpaRepositoryFactoryBean} 进行包装
     *  比如 asyncOneRepository.save(one)
     *
     * @param threadPoolEnum 执行线程池枚举
     * @param callableList 任务类
     * @param <T> 返回类型
     * @return 执行结果的Future
     */
    public <T> List<Future<T>> invokeTransactionAll(SimpleThreadPool.ThreadPoolEnum threadPoolEnum, List<Callable<T>> callableList) {

        openFlagIfNecessary();
        List<Future<T>> futures = executeAll(threadPoolEnum, callableList);
        // 不管是否开启
        INHERITABLE_THREAD.remove();
        return futures;
    }

    /**
     *  查看是否有必要开启开关
     *
     *  如果父类已经开启则不用再开，包装只是有最外层事务
     */
    private void openFlagIfNecessary() {
        if (!hasTransaction()) {
            EntityManagerHolder emHolder = (EntityManagerHolder) TransactionSynchronizationManager.getResource(jpaTransactionManager.getEntityManagerFactory());
            if (emHolder != null) {
                INHERITABLE_THREAD.set(Boolean.TRUE);
            }
        }
    }
}
