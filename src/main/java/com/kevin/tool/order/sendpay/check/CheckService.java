package com.kevin.tool.order.sendpay.check;

/**
 *  检查服务是否通过
 *
 * @author lihongmin
 * @date 2020/7/10 13:38
 * @since 1.0.0
 */
public interface CheckService {

    /**
     *  检查服务
     *
     * @return 是否检查通过，不能返回{@code null}
     */
    Boolean isCheck();
}
