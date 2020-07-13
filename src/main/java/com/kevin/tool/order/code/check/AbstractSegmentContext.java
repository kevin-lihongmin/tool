package com.kevin.tool.order.code.check;

import com.kevin.tool.order.code.SegmentState;
import lombok.Data;

/**
 *  分段配置
 * @author lihongmin
 * @date 2020/7/1 10:50
 * @since 1.0.0
 */
public abstract class AbstractSegmentContext implements Segment {

    /**
     *  段位置信息
     */
    @Data
    public static class Entry {
        public final int start;
        public final int end;

        public Entry(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    /**
     *  获取订单码对应的位置
     *
     * @param segmentState 对外枚举状态
     * @return 当前枚举对应标位
     */
    protected abstract Entry getStateConfig(SegmentState segmentState);


    @Override
    public String getSegment() {
        RequestContextParam param = CheckRequestContext.getInstance().get();
        Entry entry = getStateConfig(param.segmentState);
        return param.codeParam.getOrderCode().substring(entry.start, entry.end);
    }
}
