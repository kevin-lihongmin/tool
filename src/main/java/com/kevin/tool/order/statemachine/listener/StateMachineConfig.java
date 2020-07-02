package com.kevin.tool.order.statemachine.listener;

import com.kevin.tool.order.OrderEvent;
import com.kevin.tool.order.OrderState;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

/**
 *  {@code Spring}状态机配置
 *
 * @author lihongmin
 * @date 2020/7/1 16:11
 * @since 1.0.0
 */
@Component
@EnableStateMachine(name = "orderStateMachine")
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<OrderState, OrderEvent> {

    @Override
    public void configure(StateMachineStateConfigurer<OrderState, OrderEvent> states) throws Exception {
        states.withStates()
                .initial(OrderState.STATE1)
                .states(EnumSet.allOf(OrderState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderState, OrderEvent> transitions) throws Exception {
        transitions
            .withExternal()
            .source(OrderState.STATE1)
            .target(OrderState.STATE2)
            .event(OrderEvent.EVENT1)

            .and()
            .withExternal()
            .source(OrderState.STATE2)
            .target(OrderState.STATE1)
            .event(OrderEvent.EVENT2);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderState, OrderEvent> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true)
                .listener(new OrderStateListener());
    }

}
