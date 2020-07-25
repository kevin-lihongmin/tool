package com.kevin.tool.timeconsume;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeConsume {

    /**
     * 任务名称，用于记录时间
     *
     * @return taskName 任务名称
     */
    String taskName();

    /**
     * 打印时间消耗, 一般在执行流程的最后一步打印
     *
     * @return print default false
     */
    boolean print() default false;

}
