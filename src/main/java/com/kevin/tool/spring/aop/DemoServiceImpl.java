package com.kevin.tool.spring.aop;

/**
 *  方法的真正实现
 * @author kevin
 * @date 2019/11/12 10:47
 * @since 1.0.0
 */
public class DemoServiceImpl implements DemoService {
    @Override
    public String doSomething(String arg) {
        System.out.println("doSomething!!!");
        return "ok";
    }

    @Override
    public String doOther(String arg) {
        System.out.println("doOther!!!");
        return "other";
    }
}
