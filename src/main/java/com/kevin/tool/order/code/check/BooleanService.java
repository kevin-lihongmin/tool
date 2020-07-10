package com.kevin.tool.order.code.check;

/**
 *  标识服务
 *
 * @author lihongmin
 * @date 2020/7/10 16:07
 * @since 1.0.0
 */
public interface BooleanService {

    /**
     *  是否自动开单
     * @param code 订单码
     * @return 是否
     */
    Boolean isAutoOrder(String code);

    /**
     *  是否发送Tms标识
     * @param code 订单码
     * @return 是否
     */
    Boolean isTms(String code);
}
