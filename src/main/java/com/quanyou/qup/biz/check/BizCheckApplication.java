package com.quanyou.qup.biz.check;


import com.quanyou.qup.biz.check.config.XxlJobConfig;
import com.quanyou.qup.middle.common.performance.EnableTimeConsume;
import com.quanyou.qup.middle.common.service.enable.EnableConfShippingSiteService;
import com.quanyou.qup.security.annotation.EnableQupFeignClients;
import com.quanyou.qup.security.annotation.EnableQupResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 *  业务检查服务
 *
 * @author kevin
 * @date 2020/8/11 13:17
 * @since 1.0.0
 */
@EnableQupResourceServer
@EnableQupFeignClients
@EnableDiscoveryClient
@EnableCircuitBreaker
/*@SpringCloudApplication*/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        JdbcTemplateAutoConfiguration.class})
@EnableTimeConsume
@EnableConfShippingSiteService
@ComponentScan(basePackages = {"com.quanyou.qup"},
        excludeFilters = {@ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE,
        value = {XxlJobConfig.class/*, TimeConsumeActionConfig.class, ProductStructService.class*/})})
public class BizCheckApplication {

    public static void main(String[] args) {

        SpringApplication.run(BizCheckApplication.class, args);
    }

}
