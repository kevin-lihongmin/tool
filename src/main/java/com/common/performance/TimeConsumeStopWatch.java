package com.common.performance;

import org.springframework.util.StopWatch;


/**
 *  方法调用耗时的计时器
 * @author kevin
 * @date 2020/8/23 10:14
 * @since 1.0.0
 */
public class TimeConsumeStopWatch extends StopWatch {

    /**
     *  无参数构造，让父类处理
     */
    public TimeConsumeStopWatch() {
        super();
    }

    /**
     *  有参数构造，让父类处理
     * @param id 计时器名称
     */
    public TimeConsumeStopWatch(String id) {
        super(id);
    }

    @Override
    public String shortSummary() {
        return "'" + this.getId() + "': " + this.getTotalTimeMillis() /*/ 1000000*/ + "ms";
    }
}
