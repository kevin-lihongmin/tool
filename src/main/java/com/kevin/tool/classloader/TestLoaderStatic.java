package com.kevin.tool.classloader;

/**
 *  测试类加载器 类
 * @author kevin
 * @date 2020/9/18 16:56
 * @since 1.0.0
 */
public class TestLoaderStatic {

    static {
        System.out.println("静态代码块被执行了！");
    }
}
