package com.kevin.tool.order.statemachine.listener;

import com.kevin.tool.order.OrderEvent;
import com.kevin.tool.order.OrderState;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

/**
 *  订单状态监听
 * @author lihongmin
 * @date 2020/7/1 16:30
 * @since 1.0.0
 */
public class OrderStateListener implements StateMachineListener<OrderState, OrderEvent> {

    @Override
    public void stateChanged(State<OrderState, OrderEvent> state, State<OrderState, OrderEvent> state1) {

    }

    @Override
    public void stateEntered(State<OrderState, OrderEvent> state) {

    }

    @Override
    public void stateExited(State<OrderState, OrderEvent> state) {

    }

    @Override
    public void eventNotAccepted(Message<OrderEvent> message) {

    }

    @Override
    public void transition(Transition<OrderState, OrderEvent> transition) {

    }

    @Override
    public void transitionStarted(Transition<OrderState, OrderEvent> transition) {

    }

    @Override
    public void transitionEnded(Transition<OrderState, OrderEvent> transition) {

    }

    @Override
    public void stateMachineStarted(StateMachine<OrderState, OrderEvent> stateMachine) {

    }

    @Override
    public void stateMachineStopped(StateMachine<OrderState, OrderEvent> stateMachine) {

    }

    @Override
    public void stateMachineError(StateMachine<OrderState, OrderEvent> stateMachine, Exception e) {

    }

    @Override
    public void extendedStateChanged(Object o, Object o1) {

    }

    @Override
    public void stateContext(StateContext<OrderState, OrderEvent> stateContext) {

    }
}
