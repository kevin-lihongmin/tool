package com.kevin.tool.async.impl;

import com.kevin.tool.async.AsyncConfiguration;
import com.kevin.tool.async.Asyncable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;


/**
 *  创建订单相关线程
 * @author lihongmin
 * @date 2019/11/8 23:04
 * @since 2.1.7
 */
@Slf4j
@Component
public class CreateOrderImpl extends AsyncConfiguration {

    @Override
    @Async("createOrder")
    public Future run(Asyncable asyncable) {
        log.info("执行的当前线程为：" + Thread.currentThread().getName());
        return new AsyncResult<>(asyncable.call());
    }
}