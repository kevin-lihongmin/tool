package com.kevin.tool.spring.ioc.applicationcontext;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClassPathXmlApplicationContextTest {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring-bean.xml").getBean("kevin");
    }
}

