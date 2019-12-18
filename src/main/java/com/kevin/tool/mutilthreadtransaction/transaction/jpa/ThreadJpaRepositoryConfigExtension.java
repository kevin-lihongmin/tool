package com.kevin.tool.mutilthreadtransaction.transaction.jpa;

import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;

/**
 *  自定义返回{@link ThreadJpaRepositoryFactoryBean}类型
 *
 * @author lihongmin
 * @date 2019/12/18 16:40
 * @since 1.0.0
 */
public class ThreadJpaRepositoryConfigExtension extends JpaRepositoryConfigExtension {

    @Override
    public String getRepositoryFactoryBeanClassName() {
        return ThreadJpaRepositoryFactoryBean.class.getName();
    }
}
