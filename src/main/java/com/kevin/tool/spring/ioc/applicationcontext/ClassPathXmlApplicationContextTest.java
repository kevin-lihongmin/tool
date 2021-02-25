package com.kevin.tool.spring.ioc.applicationcontext;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *   spring {@link org.springframework.context.ApplicationContext} 源码研究入口
 * @author kevin
 * @date 2021/2/25 9:56
 * @since 1.0.0
 */
public class ClassPathXmlApplicationContextTest {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring-bean.xml").getBean("kevin");
    }
}

