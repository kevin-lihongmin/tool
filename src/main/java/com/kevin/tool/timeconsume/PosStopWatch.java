package com.kevin.tool.timeconsume;

import org.springframework.util.StopWatch;


/**
 *  方法调用耗时的计时器
 * @author lihongmin
 * @date 2019/10/25 14:14
 */
public class PosStopWatch extends StopWatch {

    /**
     *  无参数构造，让父类处理
     */
    public PosStopWatch() {
        super();
    }

    /**
     *  有参数构造，让父类处理
     * @param id 计时器名称
     */
    public PosStopWatch(String id) {
        super(id);
    }

    @Override
    public String shortSummary() {
        return "'" + this.getId() + "': " + this.getTotalTimeNanos() / 1000000 + "ms";
    }
}
