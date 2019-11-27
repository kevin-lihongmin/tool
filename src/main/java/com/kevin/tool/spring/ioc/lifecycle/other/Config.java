package com.kevin.tool.spring.ioc.lifecycle.other;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Config {

    @Bean
    public UserInfoBean initUserInfoBean() {
        return new UserInfoBean();
    }
}
