package com.kevin.tool.mybatis.plugins;


import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.context.support.AbstractApplicationContext;

import java.lang.annotation.*;
import java.util.Collection;


/**
 *  允许启动乐观锁自定义插件
 *
 * @author kevin
 * @date 2020/8/11 0:03
 * @since 1.0.0
 * @see AbstractApplicationContext#refresh()
 * @see AbstractApplicationContext#invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory)
 * @see org.springframework.context.annotation.ConfigurationClassParser#processImports(ConfigurationClass, ConfigurationClassParser.SourceClass, Collection, boolean)
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@Import(OptimisticLockConfiguration.class)
public @interface EnableOptimisticLock {

}
