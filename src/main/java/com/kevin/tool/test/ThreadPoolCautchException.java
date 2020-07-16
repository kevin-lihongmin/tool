package com.kevin.tool.test;

import com.kevin.tool.async.SimpleThreadPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadPoolCautchException {

    public static final ThreadLocal<Map> threadLocal = InheritableThreadLocal.withInitial(() -> new ConcurrentHashMap<>());

    public static void main(String[] args) {

        Map map = threadLocal.get();
        map.put("1", new HashMap<>());
        map.put("2", 1L);



        Map map1 = threadLocal.get();
        System.out.println(map1.get("2"));

        List<Callable<Object>> task = new ArrayList<>();
        task.add(() -> {
            System.out.println(map.put("3", 5L));
            return null;
        });
        SimpleThreadPool.executeAll(SimpleThreadPool.ThreadPoolEnum.CREATE_ORDER, task);

       /* threadLocal.remove();
        System.out.println(threadLocal.get().get("2"));*/
        System.out.println(threadLocal.get().get("3"));
    }
}
