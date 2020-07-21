package com.kevin.tool.order.code.check.marker;

import com.kevin.tool.order.code.CheckDTO;
import com.kevin.tool.order.code.PreposingState;
import com.kevin.tool.order.code.generate.param.CodeParam;

/**
 *  标识和 检查服务
 *
 * @author lihongmin
 * @date 2020/7/10 16:07
 * @since 1.0.0
 */
public interface MarkerCheckService extends MarkerFlagService {

    /**
     *  采购订单 检查和返回标识服务
     * @param codeParam 请求参数
     * @param preposingState 检查阶段
     * @return 检查是否通过 和 Flag是否检查标识
     */
    CheckDTO checkAndFlagChase(CodeParam codeParam, PreposingState preposingState);

    /**
     *  销售订单 检查和返回标识服务
     * @param codeParam 请求参数
     * @param preposingState 检查阶段
     * @return 检查是否通过 和 Flag是否检查标识
     */
    CheckDTO checkAndFlagSale(CodeParam codeParam, PreposingState preposingState);

}
