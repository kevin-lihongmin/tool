package com.kevin.tool;

import com.kevin.tool.timeconsume.EnableTimeConsume;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RestController;


/**
 *	启动类
 * @author lihongmin
 * @date 2019/10/25 13:45
 */
@SpringBootApplication
@RestController
@EnableTimeConsume
@EnableAspectJAutoProxy
@EnableAsync
public class KevinToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(KevinToolApplication.class, args);
	}

}
