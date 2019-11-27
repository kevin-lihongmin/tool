package com.kevin.tool.async;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;


public class ThreadPoolTest {


    public static void main(String[] args) {
        String name = Thread.currentThread().getName();
        System.out.println("当前主线程名称：" + name);
        List<Callable<Object>> tasks = new ArrayList<>();
        tasks.add(() -> {
            System.out.println("我执行了任务");
            Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
            for (Map.Entry<Thread, StackTraceElement[]> entry : allStackTraces.entrySet()) {
                System.out.println("--------------");
                System.out.println(entry.getKey().getName());
                System.out.println(entry.getValue());
                System.out.println("--------------");
            }
            return null;
        });
        SimpleThreadPool.executeAll(SimpleThreadPool.ThreadPoolEnum.CREATE_ORDER, tasks);
    }
}
