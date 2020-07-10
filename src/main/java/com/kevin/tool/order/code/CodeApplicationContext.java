package com.kevin.tool.order.code;

import com.kevin.tool.order.code.check.CheckCodeContext;
import com.kevin.tool.order.code.check.SegmentState;
import com.kevin.tool.order.code.generate.CodeFactory;
import com.kevin.tool.order.code.generate.DefaultCodeFactory;
import com.kevin.tool.order.code.generate.param.CodeParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.stereotype.Component;

import static com.kevin.tool.order.code.generate.DefaultCodeFactory.OrderType;

/**
 *  订单码业务处理容器
 *  1、订单码生成
 *  2、不同订单类型（采购订单、销售订单），在某个订单节点（比如：下单节点的验证）的检查验证；是否验证通过
 *  3、返回订单码对应标识值（Boolean类型），是否自动转单、发送Tms等动作交给客户端发起
 *
 * @author lihongmin
 * @date 2020/7/2 17:32
 * @since 1.0.0
 * @see #generateCode(CodeParam, OrderType) 订单码生成
 * @see #check(CodeParam, SegmentState) 每个节点是否检查通过
 * @see #isAutoOrder(String) 是否自动开单
 * @see #isTms(String) 是否发送Tms标识
 */
@Component
@AutoConfigureBefore(CheckCodeContext.class)
public class CodeApplicationContext extends CheckCodeContext implements CodeFactory {

    private final DefaultCodeFactory defaultCodeFactory;

    @Autowired
    public CodeApplicationContext(DefaultCodeFactory defaultCodeFactory) {
        this.defaultCodeFactory = defaultCodeFactory;
    }

    @Override
    public String generateCode(CodeParam codeParam) {
        return defaultCodeFactory.generateCode(codeParam);
    }

    @Override
    public String generateCode(CodeParam codeParam, DefaultCodeFactory.OrderType orderType) {
        return defaultCodeFactory.generateCode(codeParam, orderType);
    }

    @Override
    public String getSegment() {
        return super.getSegment();
    }
}
