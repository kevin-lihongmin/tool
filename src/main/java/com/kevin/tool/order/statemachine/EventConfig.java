package com.kevin.tool.order.statemachine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 *  注解方式的监听
 * @author lihongmin
 * @date 2020/7/1 16:40
 * @since 1.0.0
 */
@Slf4j
@WithStateMachine
public class EventConfig {

    private OrderService orderService;

    @OnTransition(target = "UNPAID")
    public void create() {
        Boolean order = orderService.createOrder();
        log.info("订单创建，待支付" + order);
    }

    @OnTransition(source = "UNPAID", target = "WAITING_FOR_RECEIVE")
    public void pay() {
        log.info("用户完成支付，待收货");
    }

    @OnTransition(source = "WAITING_FOR_RECEIVE", target = "DONE")
    public void receive() {
        log.info("用户已收货，订单完成");
    }

}