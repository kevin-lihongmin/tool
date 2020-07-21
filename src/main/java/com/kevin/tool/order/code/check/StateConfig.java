package com.kevin.tool.order.code.check;

import com.alibaba.druid.sql.ast.expr.SQLAllExpr;
import com.kevin.tool.order.code.PreposingState;
import com.kevin.tool.order.code.SegmentState;
import com.kevin.tool.order.code.check.impl.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

/**
 *  订单码配置信息
 *  当前存放到枚举中，各JVM进行管理。 后续可以考虑放到分布式配置中心等
 *
 * @author lihongmin
 * @date 2020/7/10 13:21
 * @since 1.0.0
 */
@SuppressWarnings({"unused", "unchecked"})
public enum StateConfig {

    /** 采购订单定义 */
    PURCHASE_DEFINITION(null, 1,2, null),
    /** 创建采购单阶段 */
    PURCHASE_AUDIT(SegmentState.PURCHASE_AUDIT, 3,12,
            newArrayList(CreditCheckServiceImpl.class,
                    CustomerCheckServiceImpl.class,
                    CustomerAccountCheckServiceImpl.class,
                    AddresseeCheckServiceImpl.class,
                    ProductStatusCheckServiceImpl.class)),
    /** 销售订单定义 */
    SALE_DEFINITION(null, 13,14, null),
    /** 创建订单阶段 */
    SALE_CREATE(SegmentState.SALE_CREATE,15,36,
            newArrayList(CreditCheckServiceImpl.class,
                    CustomerCheckServiceImpl.class,
                    CustomerAccountCheckServiceImpl.class,
                    AddresseeCheckServiceImpl.class,
                    ProductStatusCheckServiceImpl.class)),
    /** 预订单阶段 */
    PRE_SELL_AUDIT(SegmentState.PRE_SELL_AUDIT,37,46,
            newArrayList(CreditCheckServiceImpl.class,
                    CustomerCheckServiceImpl.class,
                    CustomerAccountCheckServiceImpl.class,
                    AddresseeCheckServiceImpl.class,
                    ProductStatusCheckServiceImpl.class)),
    /** 销售订单审核阶段 */
    SALE_AUDIT(SegmentState.SALE_AUDIT,47,56,
            newArrayList(CreditCheckServiceImpl.class,
                    CustomerCheckServiceImpl.class,
                    CustomerAccountCheckServiceImpl.class,
                    AddresseeCheckServiceImpl.class,
                    ProductStatusCheckServiceImpl.class)),

    /** 来源系统 */
    SOURCE_SYSTEM(null, 59, 60,null),

    //  以下为节点的部分 标位配置

    /** 转采购控制 */
    PURCHASE_CONTROL(null,25,26, null),
    /** 自动开单 */
    AUTO_ORDER(null,27,28, null),
    /** 转VSO控制 */
    VSO_CONTROL(null,31,32, null),
    /** 整单开单控制 */
    SINGLE_ORDER_CONTROL(null,35,36, null),
    /** 运装条件 */
    SHIPPING_CONDITION(SegmentState.SHIPPING_CONDITION,57,58, null);

    StateConfig(SegmentState segmentState, int start, int end, List<Class<? extends CheckService>> checkList) {
        this.segmentState = segmentState;
        this.start = start;
        this.end = end;
        this.checkList = checkList;
    }

    /**
     *  对应对外的枚举节点 {@link SegmentState}与 {@link StateConfig} 一对一
     */
    private final SegmentState segmentState;

    /**
     *  开始标位
     */
    private final int start;

    /**
     *  结束标位
     */
    private final int end;

    /**
     *  对应服务关系
     */
    private final List<Class<? extends CheckService>> checkList;

    public List<Class<? extends CheckService>> getCheckList() {
        return checkList;
    }

    public SegmentState getSegmentState() {
        return segmentState;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    /**
     *  枚举对应关系
     */
    private static final Map<SegmentState, StateConfig> MAP = new HashMap<>();

    static {
        for (StateConfig stateConfig : StateConfig.values()) {
            MAP.put(stateConfig.segmentState, stateConfig);
        }
    }

    /**
     * 对外提供公用查询方法
     * @param segmentState 对外枚举状态
     * @return 当前枚举
     */
    public static StateConfig getStatus(SegmentState segmentState) {
        return MAP.get(segmentState);
    }

}