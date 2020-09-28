package com.biz.check;


import com.common.performance.EnableTimeConsume;
import com.common.service.enable.EnableConfShippingSiteService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;

/**
 *  业务检查服务
 *
 * @author kevin
 * @date 2020/8/11 13:17
 * @since 1.0.0
 */
/*@EnableQupResourceServer
@EnableQupFeignClients
@EnableDiscoveryClient
@EnableCircuitBreaker*/
/*@SpringCloudApplication*/
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
//        DataSourceTransactionManagerAutoConfiguration.class,
//        JdbcTemplateAutoConfiguration.class})
//@EnableTimeConsume
//@EnableConfShippingSiteService
/*@ComponentScan(basePackages = {"com.quanyou.qup"},
        excludeFilters = {@ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE,
        value = {XxlJobConfig.class*//*, TimeConsumeActionConfig.class, ProductStructService.class*//*})})*/
public class BizCheckApplication {

    public static void main(String[] args) {

        SpringApplication.run(BizCheckApplication.class, args);
    }

}
