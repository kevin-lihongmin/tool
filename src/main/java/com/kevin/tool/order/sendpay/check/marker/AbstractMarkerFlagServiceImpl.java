package com.kevin.tool.order.sendpay.check.marker;

import com.kevin.tool.order.sendpay.check.CodeUtil;
import com.kevin.tool.order.sendpay.check.StateConfig;

/**
 *  抽象公共方法
 *
 * @author lihongmin
 * @date 2020/7/12 9:31
 * @since 1.0.0
 */
public abstract class AbstractMarkerFlagServiceImpl implements MarkerFlagService {

    /**
     *  公共标记方法
     * @param code 订单码
     * @param stateConfig 订单节点枚举
     * @return 是否需要处理，回主程序进行处理
     */
    public Boolean isFlag(String code, StateConfig stateConfig) {
        String markerCode = code.substring(stateConfig.getStart(), stateConfig.getEnd());
        return CodeUtil.isTrue(markerCode);
    }

}
