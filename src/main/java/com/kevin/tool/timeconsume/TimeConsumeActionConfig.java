package com.kevin.tool.timeconsume;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimeConsumeActionConfig {

    @Bean
    @ConditionalOnMissingBean
    public TimeConsumeAction timeConsumeAction() {
        return new TimeConsumeAction();
    }

}
