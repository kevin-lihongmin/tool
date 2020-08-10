package com.kevin.tool.mybatis.plugins;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


/**
 *  允许启动乐观锁自定义插件
 *
 * @author kevin
 * @date 2020/8/11 0:03
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@Import(OptimisticLockConfiguration.class)
public @interface EnableOptimisticLock {

}
