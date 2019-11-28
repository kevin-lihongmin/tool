package com.kevin.tool.timeconsume.service;

import com.kevin.tool.timeconsume.TimeConsume;
import com.kevin.tool.timeconsume.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


/**
 * 用户服务
 * @author lihongmin
 * @date 2019/11/23 8:39
 */
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
