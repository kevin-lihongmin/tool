package com.kevin.tool.timeconsume;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(TimeConsumeActionConfig.class)
public @interface EnableTimeConsume {
}
