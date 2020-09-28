package com.common.performance;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 *  根据策略启动是否加载
 * @author kevin
 * @date 2020/9/4 17:07
 * @since 1.0.0
 */
public class TimeConsumeActionConfig {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "time.consume", name = "enable", havingValue = "true")
    public TimeConsumeAction timeConsumeAction() {
        return new TimeConsumeAction();
    }

}
