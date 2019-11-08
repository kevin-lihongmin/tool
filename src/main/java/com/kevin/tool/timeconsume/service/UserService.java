package com.kevin.tool.timeconsume.service;

import com.kevin.tool.timeconsume.TimeConsume;
import com.kevin.tool.timeconsume.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Callable;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @TimeConsume(taskName = "UserService.getInit")
    @Async("createOrder")
    public Integer getInit() throws InterruptedException {
        Thread.sleep(3000);
        userDao.getInit();
        return 2;
    }

    @TimeConsume(taskName = "UserService.getInr", print = true)
    @Async("createOrder")
    public Integer getInr(int i) throws InterruptedException {
        Thread.sleep(2000);
        return userDao.getInr(i);
    }
}
