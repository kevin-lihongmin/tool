package com.kevin.tool.order.sendpay.check;

import com.kevin.tool.order.sendpay.PreposingStage;
import com.kevin.tool.order.sendpay.check.impl.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

/**
 *
 *
 * @author lihongmin
 * @date 2020/7/10 13:21
 * @since 1.0.0
 */
@SuppressWarnings({"unused", "unchecked"})
public enum PreposingStateConfig {

    /** 采购开单 */
    PREPOSING_PURCHASE_CREATE(PreposingStage.PURCHASE_CREATE,
            newArrayList(CreditCheckServiceImpl.class,
            CustomerCheckServiceImpl.class,
            CustomerAccountCheckServiceImpl.class,
            AddresseeCheckServiceImpl.class,
            ProductStatusCheckServiceImpl.class)),
    /** 没有采购单，直接销售开单 */
    PREPOSING_SALE_CREATE(PreposingStage.SALE_CREATE,
            newArrayList(CreditCheckServiceImpl.class,
            CustomerCheckServiceImpl.class,
            CustomerAccountCheckServiceImpl.class,
            AddresseeCheckServiceImpl.class,
            ProductStatusCheckServiceImpl.class));

    PreposingStateConfig(PreposingStage preposingStage, List<Class<? extends CheckService>> checkList) {
        this.preposingStage = preposingStage;
        this.checkList = checkList;
    }

    /**
     *  对应对外的枚举节点 {@link PreposingStage}与 {@link PreposingStateConfig} 一对一
     */
    private final PreposingStage preposingStage;

    /**
     *  对应服务关系
     */
    private final List<Class<? extends CheckService>> checkList;

    public List<Class<? extends CheckService>> getCheckList() {
        return checkList;
    }

    /**
     *  枚举对应关系
     */
    private static final Map<PreposingStage, PreposingStateConfig> MAP = new HashMap<>();

    static {
        for (PreposingStateConfig stateConfig : PreposingStateConfig.values()) {
            MAP.put(stateConfig.preposingStage, stateConfig);
        }
    }

    /**
     * 对外提供公用查询方法
     * @param preposingStage 对外枚举状态
     * @return 当前枚举
     */
    public static PreposingStateConfig getStatus(PreposingStage preposingStage) {
        return MAP.get(preposingStage);
    }

}