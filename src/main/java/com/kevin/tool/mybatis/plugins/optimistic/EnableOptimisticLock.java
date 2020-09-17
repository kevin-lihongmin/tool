package com.kevin.tool.mybatis.plugins.optimistic;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.AbstractApplicationContext;

import java.lang.annotation.*;


/**
 *  允许启动乐观锁自定义插件
 *  如果不想使用乐观锁，可以在{@code Mapper} 上加 {@link UnOptimisticLock} 注解， 前提是当前对应的Bean是单利非懒加载类型
 *
 * @author kevin
 * @date 2020/8/11 0:03
 * @since 1.0.0
 * @see AbstractApplicationContext#refresh()
 * @see AbstractApplicationContext {@code invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory) }
 * @see org.springframework.context.annotation
 *      {@code ConfigurationClassParser#processImports(ConfigurationClass, ConfigurationClassParser.SourceClass, Collection, boolean)}
 *
 * @see OptimisticLockException 需要关注跑出的检查异常，后面根据业务给予处理
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@Import(OptimisticLockConfiguration.class)
@Deprecated
public @interface EnableOptimisticLock {

}
