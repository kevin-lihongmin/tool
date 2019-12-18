package com.kevin.tool.mutilthreadtransaction.transaction.jpa;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 *  替换FactoryBean生成的{@link JpaRepository}子类的 {@link BeanDefinition#setBeanClassName(String)}
 *
 * @author lihongmin
 * @date 2019/12/18 15:44
 * @since 1.0.0
 */
@Component
public class ReplaceJpaRepository implements BeanDefinitionRegistryPostProcessor {

    /**
     *  获取实现了 {@link JpaRepository} 的BeanDefinition 设置{@link BeanDefinition#setBeanClassName(String)}
     * @param registry 注册器（本身也是{@link ConfigurableListableBeanFactory}）
     * @throws BeansException Bean异常
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        ConfigurableListableBeanFactory factory = (ConfigurableListableBeanFactory) registry;
        String[] beanNamesForType = factory.getBeanNamesForType(JpaRepository.class);
        for (String str : beanNamesForType) {
            BeanDefinition beanDefinition = factory.getBeanDefinition(str);
            beanDefinition.setBeanClassName(ThreadJpaRepositoryFactoryBean.class.getName());
            System.out.println(beanDefinition.getFactoryBeanName());
            registry.removeBeanDefinition(str);
            registry.registerBeanDefinition(str, beanDefinition);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 不做处理
    }
}
