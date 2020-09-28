package com.common.performance;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 *  开启方法耗时， 默认打印的监控名称{@link TimeConsume#taskName()} 为： 方法所在类名#方法名称
 * @author kevin
 * @date 2020/8/23 10:14
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(TimeConsumeActionConfig.class)
public @interface EnableTimeConsume {
}
