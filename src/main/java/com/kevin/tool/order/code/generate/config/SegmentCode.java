package com.kevin.tool.order.code.generate.config;

/**
 *  订单码服务
 * @author lihongmin
 * @date 2020/7/1 10:12
 * @since 1.0.0
 */
public interface SegmentCode {

    /**
     *  分段订单码
     * @return 部分订单码
     * @throws InterruptedException 线程终端异常
     */
    String configCode() throws InterruptedException;

}
