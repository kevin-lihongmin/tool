package com.kevin.tool.classloader;

/**
 *  测试类加载器执行方式，注意事项
 *
 *  Class.forName(NAME) 默认会调用 Class.forName(NAME, true, classLoader) 方法， 其中第二个参数是控制是否执行静态代码块的，默认为true，
 *                      可以调用三个参数的方法，第二个参数设置为false， 则不会执行静态代码块的东西
 *
 *  ClassLoader.loaderClass(NAME) 方法默认会调用 protected ClassLoader.loaderClass(NAME, false) 方法，不执行静态代码块的方法
 *                      如果我们想调用静态代码块的方法，只能由类加载器的子类进行重新调用， 或者使用上面的方式调用
 *
 * @author kevin
 * @date 2020/9/18 16:57
 * @since 1.0.0
 */
public class ClassLoaderTest {

    private static final String NAME = "com.kevin.tool.classloader.TestLoaderStatic";

    public static void main(String[] args) throws ClassNotFoundException {
//        new ClassLoaderTest().testForName();

        final ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        final Class<?> aClass = classLoader.loadClass(NAME);
        // 具体调用 classLoader.loadClass(NAME, false);
        System.out.println("--------------------------------------------");
    }

    /**
     * 使用 Class.forName 方式进行类加载
     * @throws ClassNotFoundException
     */
    public void testForName() throws ClassNotFoundException {
        final Class<?> aClass = Class.forName(NAME);
        System.out.println("--------------------------------------------");

        final ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
//        final Class<?> aClass2 = Class.forName(NAME, true, classLoader);

        System.out.println("--------------------------------------------");
        final Class<?> aClass1 = Class.forName(NAME, false, classLoader);
    }
}
