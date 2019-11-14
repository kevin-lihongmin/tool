package com.kevin.tool.springboot.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *  事件监听
 * @author kevin
 * @date 2019/11/6 10:50
 * @since 1.0.0
 */
@Component
public class UserListener {

    @EventListener
//    @Async
    public void getUserEvent(UserEvent userEvent) {
        System.out.println("getUserEvent-接受到事件：" + userEvent);
    }

    @EventListener
//    @Async
    public void getUserEvent2(UserEvent userEvent) {

        System.out.println("getUserEvent2-接受到事件：" + userEvent);
    }

}
