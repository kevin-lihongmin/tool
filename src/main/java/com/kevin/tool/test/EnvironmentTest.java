package com.kevin.tool.test;

import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

public class EnvironmentTest {

    public static void main(String[] args) {
        int i = Runtime.getRuntime().availableProcessors();
        System.out.println("当前机器的物理cpu的核数为 :" + i);

        Environment environment = new StandardEnvironment();
    }

}
