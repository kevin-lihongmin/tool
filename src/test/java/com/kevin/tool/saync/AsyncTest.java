package com.kevin.tool.saync;

import com.kevin.tool.KevinToolApplication;
import com.kevin.tool.async.SyncTask;
import com.kevin.tool.springboot.event.User;
import com.kevin.tool.springboot.event.UserService;
import com.kevin.tool.timeconsume.AsyncConfiguration;
import com.kevin.tool.timeconsume.AsyncUtil;
import com.kevin.tool.timeconsume.Asyncable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 *
 * @author kevin
 * @date 2019/11/6 13:30
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = KevinToolApplication.class )
@Slf4j
public class AsyncTest {

    @Autowired
    private SyncTask syncTask;

    @Test
    public void testAsync1() throws InterruptedException, ExecutionException {
        StopWatch stopWatch = new StopWatch("开始");
        StopWatch stopWatchTotal = new StopWatch("total");
        stopWatchTotal.start();
        List<Future<String>> listFuture = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            stopWatch.start("开始" + i);
            log.info("开始" + i);
            listFuture.add(syncTask.testAsync1(i));
            stopWatch.stop();
            log.info("结束" + i);
        }

        for (int i = 0; i < 5; i++) {
            Future<String> future = listFuture.get(4 - i);
            stopWatch.start("future开始" + i);
            log.info("future开始" + i);
            log.info(future.get());
            stopWatch.stop();
            log.info("future结束" + i);
        }
        log.info(stopWatch.prettyPrint());
        stopWatchTotal.stop();
        log.info("总共话费时间为：" + stopWatchTotal.shortSummary());
        System.out.println("我是不是在后面执行，拿到了前面的结果！");
    }

    @Test
    public void test2() {
        List<Asyncable<Object>> task = new ArrayList<>();
        task.add(() -> syncTask.testAsync2(3));
        task.add(() -> syncTask.testAsync3(3));
        LinkedHashSet<Object> objects = AsyncUtil.invokeAllObj(task, AsyncConfiguration.AsyncCall.CREATE_ORDER);
        Iterator<Object> iterator = objects.iterator();
        Class<? extends Iterator> aClass = iterator.getClass();
        System.out.println(aClass);
        String next = (String) iterator.next();
        Integer next1 = (Integer) iterator.next();
        log.info("得到结果1:" + next);
        log.info("得到结果2:" + next1);
    }
}
