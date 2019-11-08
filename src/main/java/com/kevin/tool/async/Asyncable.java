package com.kevin.tool.async;

import org.springframework.scheduling.annotation.Async;

/**
 *  需要异步执行的方法 {@link #call()}
 *
 * @author lihongmin
 * @date 2019/11/8 15:37
 * @since 2.1.7
 */
@FunctionalInterface
public interface Asyncable<V> {

    /**
     *  内部执行方法应该有{@link Async} 注解
     * @return 执行结果
     */
    V call();
}
