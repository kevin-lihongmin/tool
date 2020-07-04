package com.kevin.tool.order.code.check;

import com.kevin.tool.order.code.generate.param.CodeParam;

/**
 *  分段
 * @author lihongmin
 * @date 2020/7/1 10:46
 * @since 1.0.0
 */
public interface Segment {

    enum STATUS {
        /**
         *  创建采购单阶段
         */
        CREATE_PURCHASE_ORDER(0,1),
        /**
         *  创建订单阶段
         */
        CREATE_ORDER(5,10);

        STATUS(int start, int end) {
            this.start = start;
            this.end = end;
        }

        private int start;

        private int end;

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }
    }

    /**
     *  分段
     * @return 段编码
     */
    String getSegment();

    /**
     *  检查每个节点是否验证通过
     * @param codeParam 请求参数
     * @param status 节点
     * @return 是否检查通过 不会返回{@code null}
     */
    Boolean check(CodeParam codeParam, STATUS status);

}
