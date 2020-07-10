package com.kevin.tool.order.code.check;

import com.kevin.tool.order.code.generate.param.CodeParam;
import static com.kevin.tool.order.code.check.SegmentContext.Entry;
import static com.kevin.tool.order.code.generate.DefaultCodeFactory.OrderType;

/**
 *  请求参数封装
 *
 * @author lihongmin
 * @date 2020/7/1 13:25
 * @since 1.0.0
 */
public class RequestContextParam {

    public final CodeParam codeParam;

    public final SegmentState segmentState;

    public final Entry entry;

    public final OrderType orderType;

    public RequestContextParam(CodeParam codeParam, SegmentState segmentState, Entry entry) {
        this.codeParam = codeParam;
        this.segmentState = segmentState;
        this.entry = entry;
        this.orderType = null;
    }

    public RequestContextParam(CodeParam codeParam, OrderType orderType) {
        this.codeParam = codeParam;
        this.orderType = orderType;
        this.segmentState = null;
        this.entry = null;
    }
}
