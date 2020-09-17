package com.kevin.tool.mybatis.plugins.optimistic;


import java.lang.annotation.*;

/**
 *  不想启动乐观锁，注解标识
 *
 * @author kevin
 * @date 2020/8/11 14:08
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Configuration
public @interface UnOptimisticLock {

}
