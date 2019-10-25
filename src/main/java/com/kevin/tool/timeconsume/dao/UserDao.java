package com.kevin.tool.timeconsume.dao;

import com.kevin.jpa.timeconsume.TimeConsume;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@TimeConsume(taskName = "dao", print = true)
public class UserDao {

    @Async
    public void getInit() throws InterruptedException {
        Thread.sleep(1000);
        log.info("111");
    }

    @TimeConsume(taskName = "dao.getInr", print = true)
    public int getInr(int i) throws InterruptedException {
        Thread.sleep(20);
        System.out.println("222");
        return 2 + i;
    }
}
