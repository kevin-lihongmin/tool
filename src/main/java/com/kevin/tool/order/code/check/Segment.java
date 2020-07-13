package com.kevin.tool.order.code.check;

import com.kevin.tool.order.code.SegmentState;
import com.kevin.tool.order.code.generate.param.CodeParam;

/**
 *  分段
 * @author lihongmin
 * @date 2020/7/1 10:46
 * @since 1.0.0
 */
public interface Segment {

    /**
     *  分段
     * @return 段编码
     */
    String getSegment();

    /**
     *  检查每个节点是否验证通过
     * @param codeParam 请求参数
     * @param segmentState 节点
     * @return 是否检查通过 不会返回{@code null}
     */
    Boolean check(CodeParam codeParam, SegmentState segmentState);

}
