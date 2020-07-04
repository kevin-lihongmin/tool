package com.kevin.tool.order.code.check;

import com.kevin.tool.order.code.generate.param.CodeParam;

/**
 *  请求参数封装
 *
 * @author lihongmin
 * @date 2020/7/1 13:25
 * @since 1.0.0
 */
public class RequestContextParam {

    public final CodeParam codeParam;

    public final Segment.STATUS status;

    public RequestContextParam(CodeParam codeParam, Segment.STATUS status) {
        this.codeParam = codeParam;
        this.status = status;
    }
}
