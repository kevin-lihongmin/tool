package com.kevin.tool.test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 *  主线程不处理线程池中的任何异常
 *  主要场景，比如在一个方法中有事务，则肯定需要把子线程中的异常抛出，进行回滚
 *
 *  222
 * 111
 * java.util.concurrent.ExecutionException: java.lang.RuntimeException: 111发生错误
 * 	at java.util.concurrent.FutureTask.report(FutureTask.java:122)
 * 	at java.util.concurrent.FutureTask.get(FutureTask.java:192)
 * 	at com.kevin.jpa.threadpool.ThreadExceptionTest.withException(ThreadExceptionTest.java:38)
 * 	at com.kevin.jpa.threadpool.ThreadExceptionTest.main(ThreadExceptionTest.java:9)
 * Caused by: java.lang.RuntimeException: 111发生错误
 * 	at com.kevin.jpa.threadpool.ThreadExceptionTest$1.run(ThreadExceptionTest.java:28)
 * 	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
 * 	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
 * 	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
 * 	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
 * 	at java.lang.Thread.run(Thread.java:744)
 *
 * @author lihongmin
 * @date 2019/10/24 13:54
 */
public class ThreadPoolExceptionTest {

    public static void main(String[] args) {
        try {
            new ThreadPoolExceptionTest().withException();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     *  主线程不管异常，直接抛出
     */
    public void withException() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Future<?> submit = executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("111");
                throw new RuntimeException("111发生错误");
            }
        });
        Future<?> submit1 = executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("222");
            }
        });

        Object o = submit.get();
        Object o2 = submit1.get();
        System.out.println();

    }
}
