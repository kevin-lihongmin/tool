package com.common.interceptor;


import java.lang.annotation.*;


/**
 * Mybatis Mapper返回Map类型注解
 *
 * @author zhouwei6
 * @date 2020/09/25
 * @since 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Inherited
public @interface MapResult {
    String value();
}
