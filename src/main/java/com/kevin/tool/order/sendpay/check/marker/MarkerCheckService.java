package com.kevin.tool.order.sendpay.check.marker;

import com.kevin.tool.order.sendpay.CheckDTO;
import com.kevin.tool.order.sendpay.PreposingStage;
import com.kevin.tool.order.sendpay.generate.param.CodeParam;

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
     * @param preposingStage 检查阶段
     * @return 检查是否通过 和 Flag是否检查标识
     */
    CheckDTO chaseCheckAndFlag(CodeParam codeParam, PreposingStage preposingStage);

    /**
     *  销售订单 检查和返回标识服务
     * @param codeParam 请求参数
     * @param preposingStage 检查阶段
     * @return 检查是否通过 和 Flag是否检查标识
     */
    CheckDTO saleCheckAndFlag(CodeParam codeParam, PreposingStage preposingStage);

}
