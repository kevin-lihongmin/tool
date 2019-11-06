package com.kevin.tool.springboot.event;

import org.springframework.context.ApplicationEvent;

/**
 *  用户事件
 * @author kevin
 * @date 2019/11/6 10:45
 * @since 1.0.0
 */
public class UserEvent extends ApplicationEvent {

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public UserEvent(Object source) {
        super(source);
    }
}
