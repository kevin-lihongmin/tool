package com.kevin.tool.timeconsume.service;

import com.kevin.jpa.timeconsume.TimeConsume;
import com.kevin.jpa.timeconsume.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @TimeConsume(taskName = "service.getInit")
    public int getInit() throws InterruptedException {
        Thread.sleep(1000);
        userDao.getInit();
        return 2;
    }

    @TimeConsume(taskName = "service.getInr", print = true)
    public int getInr(int i) throws InterruptedException {
        Thread.sleep(2000);
        return userDao.getInr(i);
    }
}
