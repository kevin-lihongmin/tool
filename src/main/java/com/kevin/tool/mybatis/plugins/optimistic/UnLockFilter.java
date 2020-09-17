package com.kevin.tool.mybatis.plugins.optimistic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 *  不加乐观锁过滤， 只对单利非懒加载的Mapper进行过滤
 *
 *  不使用{@code Spring IOC}的生命周期， 执行完之后在{@code Spring Boot} 生命周期最后加载不使用乐观锁列表
 *
 * @author kevin
 * @date 2020/8/11 14:12
 * @since 1.0.0
 */
@Slf4j
public class UnLockFilter implements ApplicationContextAware, ApplicationRunner {

    /**
     * Spring IOC 容器
     */
    private ApplicationContext applicationContext;

    /**
     *  需要过滤乐观锁的Bean
     */
    private Map<String, Class<?>> filterSkipListMap = new ConcurrentSkipListMap<>();

    @Override
    public void run(ApplicationArguments args) {
        // 在容器加载的最后，保证所有refresh最后一步把所有单利非懒加载的Bean 调用getBean完成
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(UnOptimisticLock.class);
        log.info("UnOptimisticLock list:" + beansWithAnnotation.values());
        beansWithAnnotation.forEach((key, value) -> filterSkipListMap.put(key, value.getClass()));
        filterSkipListMap = Collections.unmodifiableMap(filterSkipListMap);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     *  是否应该跳过乐观锁处理
     *
     * @param clazz 当前类
     * @return 是否应该跳过
     */
    protected Boolean shouldSkip(Class<?> clazz) {
        if (filterSkipListMap.isEmpty()) {
            return false;
        }
        return filterSkipListMap.containsValue(clazz);
    }

}
