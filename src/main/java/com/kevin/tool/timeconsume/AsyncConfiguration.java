package com.kevin.tool.timeconsume;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import static com.kevin.tool.timeconsume.SimpleThreadPool.*;

@Configuration
public class AsyncConfiguration {

    public enum AsyncCall {

        CREATE_ORDER {
            @Async("create_order")
            public Future<Object> run(Asyncable<Object> asyncable) {
                return new AsyncResult<>(asyncable.call());
            }
        };

        public abstract Future<Object> run(Asyncable<Object> asyncable);
    }


    @Bean("create_order")
    public ThreadPoolExecutor initCreateOrder() {
        return THREAD_POOL_EXECUTOR_MAP.get(ThreadPoolEnum.CREATE_ORDER);
    }


}
