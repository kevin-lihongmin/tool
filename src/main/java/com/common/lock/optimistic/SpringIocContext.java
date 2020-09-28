//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.common.lock.optimistic;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 *  Bean ioc 静态工具类
 * @author kevin
 * @date 2020/9/17 13:21
 * @since 1.0.0
 */
@Component
public class SpringIocContext implements ApplicationContextAware {

    public static ApplicationContext applicationContext;

    public SpringIocContext() {
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) {
        SpringIocContext.applicationContext = applicationContext;
    }

    /**
     * 获取带有 注解的类
     * @param annotationClass 注解类
     * @return 标注了注解的Bean
     */
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationClass) {
        try {
            return applicationContext.getBeansWithAnnotation(annotationClass);
        } catch (Exception var2) {
            return null;
        }
    }

}
