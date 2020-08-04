package com.kevin.tool.order.sendpay.check;

import com.kevin.tool.order.sendpay.Stage;
import com.kevin.tool.order.sendpay.check.impl.*;

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
    PURCHASE_AUDIT(Stage.PURCHASE_AUDIT, 3,12,
            newArrayList(CreditCheckServiceImpl.class,
                    CustomerCheckServiceImpl.class,
                    CustomerAccountCheckServiceImpl.class,
                    AddresseeCheckServiceImpl.class,
                    ProductStatusCheckServiceImpl.class)),
    /** 销售订单定义 */
    SALE_DEFINITION(null, 13,14, null),
    /** 销售开单【货源安排】阶段 */
    SALE_CREATE(Stage.SALE_CREATE,15,40,
            newArrayList(CreditCheckServiceImpl.class,
                    CustomerCheckServiceImpl.class,
                    CustomerAccountCheckServiceImpl.class,
                    AddresseeCheckServiceImpl.class,
                    ProductStatusCheckServiceImpl.class)),
    /** VSO转SO阶段 */
    VSO_TO_SO(Stage.VSO_TO_SO,40,50,
            newArrayList(CreditCheckServiceImpl.class,
                    CustomerCheckServiceImpl.class,
                    CustomerAccountCheckServiceImpl.class,
                    AddresseeCheckServiceImpl.class,
                    ProductStatusCheckServiceImpl.class)),
    /** 预订单阶段 */
    PRE_SELL_AUDIT(Stage.PRE_SELL_AUDIT,51,60,
            newArrayList(CreditCheckServiceImpl.class,
                    CustomerCheckServiceImpl.class,
                    CustomerAccountCheckServiceImpl.class,
                    AddresseeCheckServiceImpl.class,
                    ProductStatusCheckServiceImpl.class)),
    /** 销售订单审核阶段 */
    SALE_AUDIT(Stage.SALE_AUDIT,60,70,
            newArrayList(CreditCheckServiceImpl.class,
                    CustomerCheckServiceImpl.class,
                    CustomerAccountCheckServiceImpl.class,
                    AddresseeCheckServiceImpl.class,
                    ProductStatusCheckServiceImpl.class)),

    /** 来源系统 */
    SOURCE_SYSTEM(null, 71, 72,null),

    //  以下为节点的部分 标位配置

    /** 转采购控制 */
    PURCHASE_CONTROL(null,25,26, null),
    /** 自动开单 */
    AUTO_ORDER(null,27,28, null),
    /** 转SO控制 */
    SO_CONTROL(null,29,30, null),
    /** SO 是否检查 Atp */
    SO_ATP_CONTROL(null,33,34, null),
    /** 转VSO控制 */
    VSO_CONTROL(null,33,34, null),
    /** VSO：预销售检查 */
    PRE_SELL_CONTROL(null,35,36, null),
    /** 销售开单：是否紧缺检查 */
    SALE_SHORTAGE(null,37,38, null),
    /** 整单开单控制 */
    SINGLE_ORDER_CONTROL(null,39,40, null),
    /** VSO转SO ：是否紧缺检查 */
    VSO_TO_SO_SHORTAGE(null,49,50, null),
    /** 运装条件 */
    SHIPPING_CONDITION(Stage.SHIPPING_CONDITION,69,70, null);

    StateConfig(Stage stage, int start, int end, List<Class<? extends CheckService>> checkList) {
        this.stage = stage;
        this.start = start;
        this.end = end;
        this.checkList = checkList;
    }

    /**
     *  对应对外的枚举节点 {@link Stage}与 {@link StateConfig} 一对一
     */
    private final Stage stage;

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

    public Stage getStage() {
        return stage;
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
    private static final Map<Stage, StateConfig> MAP = new HashMap<>();

    static {
        for (StateConfig stateConfig : StateConfig.values()) {
            MAP.put(stateConfig.stage, stateConfig);
        }
    }

    /**
     * 对外提供公用查询方法
     * @param stage 对外枚举状态
     * @return 当前枚举
     */
    public static StateConfig getStatus(Stage stage) {
        return MAP.get(stage);
    }

}