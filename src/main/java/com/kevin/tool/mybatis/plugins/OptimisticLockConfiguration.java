package com.kevin.tool.mybatis.plugins;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;


/**
 *  乐观锁配置
 *
 * @author kevin
 * @date 2020/8/11 0:06
 * @since 1.0.0
 */
//@Configuration
@AutoConfigureOrder(value = Integer.MAX_VALUE)
public class OptimisticLockConfiguration {

    /**
     *  依赖注入 乐观锁插件的Bean
     * @return 乐观锁插件
     */
    @Bean(autowire = Autowire.BY_TYPE, name = "optimisticLock")
    @ConditionalOnMissingBean(OptimisticLock.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public OptimisticLock initOptimisticLock() {
        return new OptimisticLock();
    }

}
