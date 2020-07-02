package com.kevin.tool.order.code.check;

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

}
