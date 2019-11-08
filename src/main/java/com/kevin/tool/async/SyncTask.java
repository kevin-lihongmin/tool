package com.kevin.tool.async;

import com.kevin.tool.timeconsume.Asyncable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Future;

@Component
public class SyncTask {

    @Async("create_order")
    public Future<String> testAsync1(int i) {
        try {
            Thread.sleep(5000 / (i + 1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
        return new AsyncResult<>("testAsync-->out"+i);
    }

    public String testAsync2(int i) {
        try {
            Thread.sleep(5000 / (i + 1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("testAsync2" + Thread.currentThread().getName());
        return "hello!!!";
    }

    @Async("create_order")
    public Future<Object> test(Asyncable asyncable) {
        return new AsyncResult<>(asyncable.call());
    }

    public Integer testAsync3(int i) {
        try {
            Thread.sleep(5000 / (i + 1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("testAsync3" + Thread.currentThread().getName());
        return 5;
    }


}
