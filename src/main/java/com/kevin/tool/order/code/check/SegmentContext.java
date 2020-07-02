package com.kevin.tool.order.code.check;

import lombok.Data;

import java.util.*;

/**
 *  分段配置
 * @author lihongmin
 * @date 2020/7/1 10:50
 * @since 1.0.0
 */
public class SegmentContext implements Segment {

    protected static Set<STATUS> index;

    static {
        Set<STATUS> map = new HashSet<>();
        for (STATUS value : STATUS.values()) {
            index.add(value);
        }
        index = Collections.unmodifiableSet(map);
    }

    /**
     *  段位置信息
     */
    @Data
    class Entry{
        private int start;
        private int end;
    }

    @Override
    public String getSegment() {
        RequestContextParam param = CheckRequestContext.getInstance().get();
        return param.codeParam.getOrderCode().substring(param.status.getStart(), param.status.getEnd());
    }
}
