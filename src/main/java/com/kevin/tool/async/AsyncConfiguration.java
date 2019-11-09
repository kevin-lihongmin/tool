package com.kevin.tool.async;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.scheduling.annotation.Async;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import static com.kevin.tool.async.SimpleThreadPool.THREAD_POOL_EXECUTOR_MAP;
import static com.kevin.tool.async.SimpleThreadPool.ThreadPoolEnum;


/**
 *  注入知道的线程池bean， 异步相关配置 {@link Async}
 * @author lihongmin
 * @date 2019/11/8 23:02
 * @since 2.1.7
 * @see SimpleThreadPool#THREAD_POOL_EXECUTOR_MAP
 * @see SimpleThreadPool.ThreadPoolEnum
 */
@Configuration
@Slf4j
public abstract class AsyncConfiguration implements ImportBeanDefinitionRegistrar {

    /**
     *  需要注入线程池的bean
     */
    private static final Set<SimpleThreadPool.ThreadPoolEnum> REGIST_THREAD_POOL = Collections.unmodifiableSet(
            Sets.newHashSet(ThreadPoolEnum.CREATE_ORDER)
    );

    /**
     *  动态注入bean
     * @param importingClassMetadata 注入元信息
     * @param registry 注入器
     * @see org.springframework.context.support.AbstractApplicationContext
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        REGIST_THREAD_POOL.forEach(pool -> {
            ThreadPoolExecutor threadPoolExecutor = THREAD_POOL_EXECUTOR_MAP.get(pool);
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(ThreadPoolExecutor.class)
                    .addConstructorArgValue(threadPoolExecutor);
            registry.registerBeanDefinition(pool.taskName, builder.getBeanDefinition());
        });

    }

    /**
     *  每个子类线程池实现自己的run
     * @param asyncable 执行回调
     * @return 直接结果
     */
    public abstract Future run(Asyncable asyncable);

    /*@Bean("createOrder")
    public ThreadPoolExecutor initCreateOrder() {
        return THREAD_POOL_EXECUTOR_MAP.get(ThreadPoolEnum.CREATE_ORDER);
    }
*/

}
