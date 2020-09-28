package com.common.performance;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  耗时打印标记
 *
 *  如果使用总开关{@link EnableTimeConsume} 的关闭不起作用需要：
 *
 *  @ComponentScan(value = "com.quanyou.qup",
 *         excludeFilters = {@ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE,
 *                 value = {TimeConsumeActionConfig.class})})
 *
 * @author kevin
 * @date 2020/8/23 10:24
 * @since 1.0.0
 */
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeConsume {

    /**
     * 任务名称，用于记录时间
     *
     * @return taskName 任务名称
     */
    String taskName() default "";

    /**
     * 打印时间消耗, 一般在执行流程的最后一步打印
     * 为防止流氓扫描注入，开关失效
     *
     * @return 是否打印日志
     */
    boolean print() default true;

}
