package com.kevin.tool.order.sendpay.check;

import com.kevin.tool.order.sendpay.Stage;
import com.kevin.tool.order.sendpay.generate.param.CodeParam;

import static com.kevin.tool.order.sendpay.check.AbstractSegmentContext.Entry;
import static com.kevin.tool.order.sendpay.generate.DefaultCodeFactory.OrderType;

/**
 *  请求参数封装
 *
 * @author lihongmin
 * @date 2020/7/1 13:25
 * @since 1.0.0
 */
public final class RequestContextParam {

    public final CodeParam codeParam;

    public final Stage stage;

    public final Entry entry;

    public final OrderType orderType;

    public RequestContextParam(CodeParam codeParam, Stage stage, Entry entry) {
        this.codeParam = codeParam;
        this.stage = stage;
        this.entry = entry;
        this.orderType = null;
    }

    public RequestContextParam(CodeParam codeParam, OrderType orderType) {
        this.codeParam = codeParam;
        this.orderType = orderType;
        this.stage = null;
        this.entry = null;
    }
}
