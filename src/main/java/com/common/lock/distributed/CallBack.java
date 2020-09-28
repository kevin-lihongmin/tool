package com.common.lock.distributed;

/**
 *  预销销售计划回调任务
 * @author kevin
 * @date 2020/9/2 9:23
 * @since 1.0.0
 */
@FunctionalInterface
public interface CallBack<V> {

    /**
     * 需要回调执行的方法体
     * @return 执行结果
     */
    V callBack();
}