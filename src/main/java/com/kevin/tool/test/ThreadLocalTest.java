package com.kevin.tool.test;

import org.springframework.util.StopWatch;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;


/**
 *  测试 {@link ThreadLocal} 的功能Tool
 *
 * @author lihongmin
 * @date 2019/10/24 9:33
 */
public class ThreadLocalTest {

    public static void main(String[] args) throws InterruptedException {
        new ThreadLocalTest().threadLocalWithInitial(true);
    }

    /**
     *  验证：
     *  测试使用 {@link ThreadLocal#withInitial(Supplier)}初始化的值，
     *  是否每个线程获取到的是不同的对象
     *
     *  返回结果：
     *  isOuter == false：
     *
     *  pool-1-thread-1:11
     *  pool-1-thread-1:202525796
     *  ------------------------
     *  pool-1-thread-2:1
     *  pool-1-thread-2:1339694342
     *  ------------------------
     *  pool-1-thread-1:11
     *  pool-1-thread-1:202525796
     *
     *  isOuter == true ：
     *  713338599
     *  ------------------------
     *  pool-1-thread-1:11
     *  pool-1-thread-1:713338599
     *  ------------------------
     *  pool-1-thread-2:11
     *  pool-1-thread-2:713338599
     *  ------------------------
     *  pool-1-thread-1:11
     *  pool-1-thread-1:713338599
     */
    public void threadLocalWithInitial(Boolean isOuter) {
        ThreadLocal<AtomicInteger> Thread_Local = getAtomicIntegerThreadLocal(isOuter);
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                AtomicInteger atomicInteger = Thread_Local.get();
                atomicInteger.getAndAdd(10);
                String name = Thread.currentThread().getName();
                System.out.println(name + ":" + atomicInteger.intValue());
                System.out.println(name + ":" + System.identityHashCode(atomicInteger));
            }
        });
        // 第二个线程获取
        printThreadLocalInit(Thread_Local, executorService);
        // 其他线程获取
        printThreadLocalInit(Thread_Local, executorService);

        executorService.shutdown();
    }

    /**
     *  获取不同初始化方式的{@link ThreadLocal} 对象
     *
     * @param isOuter 是否初始化执行再传入 {@link ThreadLocal#withInitial(Supplier)}方法
     * @return ThreadLocal对象
     */
    private ThreadLocal<AtomicInteger> getAtomicIntegerThreadLocal(boolean isOuter) {
        if (isOuter) {
            AtomicInteger init = new AtomicInteger(1);
            System.out.println(System.identityHashCode(init));
            return ThreadLocal.withInitial(() -> init);
        } else {
            return ThreadLocal.withInitial(() -> new AtomicInteger(1));
        }

    }

    /**
     *  用线程池的其他线程，获取ThreadLocal中的初始化值
     *
     * @param threadLocal
     * @param executorService
     */
    private void printThreadLocalInit(ThreadLocal<AtomicInteger> threadLocal, ExecutorService executorService) {
        System.out.println("------------------------");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                AtomicInteger atomicInteger = threadLocal.get();
                String name = Thread.currentThread().getName();
                System.out.println(name + ":" + atomicInteger.intValue());
                System.out.println(name + ":" + System.identityHashCode(atomicInteger));
            }
        });
    }

    public void testString() throws InterruptedException {
/*
        String s = "123";
        String s1 = "456";
        String s3 = "789";
        System.out.println(System.identityHashCode(s));
        System.out.println(System.identityHashCode(s1));
        System.out.println(System.identityHashCode(s3));
        String str = new StringBuilder(s + s1 + s3).toString();
        System.out.println(str.hashCode());*/




    }

    public void testStopWatch() {
        StopWatch stopWatch = new StopWatch("kevin");
        stopWatch.start("方法开始了");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    private void objEquals() {
        Long a = 614298525295448064L;
        Long b = 614298525295448064L;
        boolean equals = Objects.equals(a, b);
        System.out.println(equals);
    }
}
