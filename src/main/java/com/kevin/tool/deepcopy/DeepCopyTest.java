package com.kevin.tool.deepcopy;

import com.esotericsoftware.kryo.Kryo;
import net.sf.json.JSONObject;
import org.springframework.util.StopWatch;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *  深度拷贝类型        循环次数[1000]      循环次数[10000]      循环次数[1000000]
 *  new               5 ms               14 ms              133 ms
 *
 *  Cloneable:        < 1 ms             7 ms               88 ms
 *
 *  Jdk序列化:         272 ms             1589 ms            66190 ms
 *
 *  Kryo序列化:        95 ms              123 ms             2438 ms
 *
 *  Json序列化:        1203 ms            3746 ms            163512 ms
 *
 *  总结： 1)、序列化性能   Clone > new > Kryo序列化 > Jdk序列化 > Json(各种Json类似)序列化
 *        2)、Clone深拷贝性能最高，但是如果属性中有特定的对象字段，则需要自己编写代码
 *        3)、new 性能仅次于Clone，因为需要执行Jvm过程（常量池判断，内存分配，值初始化，init方法调用，栈中对象的引用等），并且主要是每个对象需要单独编写代码，当然也不建议使用反射
 *        4)、kryo 性能较高，并且不需要单独的开发，  若对性能不是特别高，可以考虑使用.(kryo是非线程安全的，项目中使用时可以放入ThreadLocal中)
 *        5)、Jdk序列化和Json序列化，性能太低，高性能项目不建议使用
 *
 *  总结的总结： 如果性能要求特别高(或者对象结构层次不深)，可以使用Clone方式；否则可以考虑使用 Kryo序列化和反序列化实现对象深拷贝
 *
 * @author kevin
 * @date 2020/9/27 13:45
 * @since 1.0.0
 */
public class DeepCopyTest {

    /**
     * 循环的次数
     */
    private static final int LOOP = 1000;

    private static Kryo kryo = new Kryo();

    public static void main(String[] args) throws Exception {
        DeepCopyEntity demo = getInit();

        StopWatch stopWatch = new StopWatch("测试深拷贝");
        stopWatch.start();
        for (int i = 0; i < LOOP; i++) {
//            DeepCopyEntity deep = newObject(demo);
            final DeepCopyEntity deep = demo.clone();
//            final DeepCopyEntity deepCopyEntity = copyImplSerializable(demo);
//            final DeepCopyEntity deepCopyEntity = copyByKryo(demo);
//            final DeepCopyEntity deepCopyEntity1 = copyByJson(demo);
        }
        stopWatch.stop();

        System.out.println(stopWatch.prettyPrint());
    }

    /**
     * 深层拷贝 - 需要net.sf.json.JSONObject
     * @param <T>
     * @param obj
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T copyByJson(T obj) throws Exception {
        return (T) JSONObject.toBean(JSONObject.fromObject(obj),obj.getClass());
    }

    /**
     *
     * @param source
     * @return
     */
public static <T> T copyByKryo(T source){
    return kryo.copy(source);
}

/**
 * 深层拷贝 - 需要类继承序列化接口
 * @param <T> 对象类型
 * @param obj 原对象
 * @return 深度拷贝的对象
 * @throws Exception
 * @see java.io.Closeable
 * @see AutoCloseable 不用进行关闭
 */
@SuppressWarnings("unchecked")
public static <T> T copyImplSerializable(T obj) throws Exception {
    ByteArrayOutputStream baos = null;
    ObjectOutputStream oos = null;

    ByteArrayInputStream bais = null;
    ObjectInputStream ois = null;

    Object o = null;
    //如果子类没有继承该接口，这一步会报错
    try {
        baos = new ByteArrayOutputStream();
        oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        bais = new ByteArrayInputStream(baos.toByteArray());
        ois = new ObjectInputStream(bais);

        o = ois.readObject();
        return (T) o;
    } catch (Exception e) {
        throw new Exception("对象中包含没有继承序列化的对象");
    }
}


    private static DeepCopyEntity newObject(DeepCopyEntity demo) {
        final DeepCopyEntity deepCopyEntity = new DeepCopyEntity();
        deepCopyEntity.setId(demo.getId());
        deepCopyEntity.setField1(demo.getField1());
        deepCopyEntity.setField2(demo.getField2());
        deepCopyEntity.setField3(demo.getField1());
        deepCopyEntity.setField4(demo.getField1());
        deepCopyEntity.setField5(demo.getField1());
        deepCopyEntity.setField6(demo.getField1());
        deepCopyEntity.setField7(demo.getField1());
        deepCopyEntity.setField8(demo.getField1());
        deepCopyEntity.setField9(demo.getField1());
        deepCopyEntity.setField10(demo.getField1());
        deepCopyEntity.setField11(demo.getField1());
        deepCopyEntity.setField12(demo.getField1());
        deepCopyEntity.setField13(demo.getField1());
        deepCopyEntity.setField14(demo.getField1());
        deepCopyEntity.setField15(demo.getField1());
        deepCopyEntity.setField16(demo.getField1());
        deepCopyEntity.setField17(demo.getField1());
        deepCopyEntity.setField18(demo.getField1());
        deepCopyEntity.setField19(demo.getField1());
        deepCopyEntity.setField20(demo.getField1());
        deepCopyEntity.setField21(demo.getField1());
        deepCopyEntity.setField22(demo.getField1());
        deepCopyEntity.setField23(demo.getField1());
        deepCopyEntity.setField24(demo.getField1());
        deepCopyEntity.setField25(demo.getField1());
        deepCopyEntity.setField26(demo.getField1());
        deepCopyEntity.setField27(demo.getField1());
        deepCopyEntity.setField28(demo.getField1());
        deepCopyEntity.setField29(demo.getField1());
        deepCopyEntity.setField30(demo.getField1());
        deepCopyEntity.setField31(demo.getField1());
        deepCopyEntity.setField32(demo.getField1());
        deepCopyEntity.setField33(demo.getField1());
        deepCopyEntity.setField34(demo.getField1());
        deepCopyEntity.setField35(demo.getField1());
        deepCopyEntity.setField36(demo.getField1());
        deepCopyEntity.setField37(demo.getField1());
        deepCopyEntity.setField38(demo.getField1());
        deepCopyEntity.setField39(demo.getField1());
        deepCopyEntity.setField40(demo.getField1());
        deepCopyEntity.setField41(demo.getField1());
        deepCopyEntity.setField42(demo.getField1());
        deepCopyEntity.setField43(demo.getField1());
        deepCopyEntity.setField44(demo.getField1());
        deepCopyEntity.setField45(demo.getField1());
        deepCopyEntity.setField46(demo.getField1());
        deepCopyEntity.setField47(demo.getField1());
        deepCopyEntity.setField48(demo.getField1());
        deepCopyEntity.setField49(demo.getField1());
        deepCopyEntity.setField50(demo.getField1());



        return deepCopyEntity;
    }


    /**
     * 获取初始化值
     * @return demo对象
     */
    private static DeepCopyEntity getInit() {
        final DeepCopyEntity deepCopyEntity = new DeepCopyEntity();
        deepCopyEntity.setId("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField1("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField2("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField3("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField4("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField5("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField6("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField7("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField8("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField9("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField10("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField11("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField12("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField13("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField14("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField15("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField16("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField17("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField18("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField19("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField20("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField21("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField22("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField23("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField24("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField25("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField26("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField27("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField28("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField29("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField30("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField31("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField32("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField33("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField34("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField35("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField36("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField37("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField38("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField39("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField40("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField41("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField42("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField43("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField44("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField45("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField46("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField47("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField48("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField49("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");
        deepCopyEntity.setField50("测试字段进来撒个是个是个复活节快乐时刻六公里按时交付格拉斯可根据ask了接受了嘎嘎健康金克拉是个零售价格克拉斯关键时刻两个jklsghbld时间噶设立国家级法国设计规划拉萨尽快赶回监考老师的风格就是看来撒骨灰两个据类");

        return deepCopyEntity;
    }
}
