package com.kevin.tool.mybatis.plugins.optimistic;

/**
 *  乐观锁更新异常
 *
 * @author kevin
 * @date 2020/8/11 9:26
 * @since 1.0.0
 */
public class OptimisticLockException extends Exception {

    /**
     *  异常信息【sql】
     */
    private String message;

    public OptimisticLockException(String message) {
        this.message = message;
    }
}
