package com.kevin.tool.spring.ioc.lifecycle.other;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChangeUserInfoBean implements InitializingBean {

    @Autowired
    UserInfoBean userInfoBean;

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
