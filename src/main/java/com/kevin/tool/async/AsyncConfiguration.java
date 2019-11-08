package com.kevin.tool.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import static com.kevin.tool.async.SimpleThreadPool.*;


/**
 *  异步相关配置 {@link Async}
 * @author lihongmin
 * @date 2019/11/8 23:02
 * @since 2.1.7
 */
@Configuration
@Slf4j
public abstract class AsyncConfiguration {

    /**
     *  每个子类线程池实现自己的run
     * @param asyncable 执行回调
     * @return 直接结果
     */
    public abstract Future run(Asyncable asyncable);

    @Bean("createOrder")
    public ThreadPoolExecutor initCreateOrder() {
        return THREAD_POOL_EXECUTOR_MAP.get(ThreadPoolEnum.CREATE_ORDER);
    }


}
