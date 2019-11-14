package com.kevin.tool.springboot.event;

import org.springframework.beans.BeansException;
import org.springframework.context.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("eventUserService")
public class UserService implements ApplicationContextAware, ApplicationEventPublisherAware {

    private ApplicationContext applicationContext;
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Async
    public String addUser(User user) {
        // 保存用户
        user.setId(1L);
        user.setName("name-1");
        // 发生事件（发邮件、发短信、、、）
        applicationContext.publishEvent(new UserEvent(user));
        // 两种发生方式一致
        applicationEventPublisher.publishEvent(new UserEvent(user));
        return "ok";
    }

}
