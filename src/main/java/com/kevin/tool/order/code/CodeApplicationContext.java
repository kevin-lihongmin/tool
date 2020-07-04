package com.kevin.tool.order.code;

import com.kevin.tool.order.code.check.CheckCodeContext;
import com.kevin.tool.order.code.check.Segment;
import com.kevin.tool.order.code.generate.CodeFactory;
import com.kevin.tool.order.code.generate.DefaultCodeFactory;
import com.kevin.tool.order.code.generate.param.CodeParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.stereotype.Component;

/**
 *  订单码业务处理容器
 *  1、订单码生成
 *  2、不同订单类型（采购订单、销售订单），在某个订单节点（比如：下单节点的验证）的检查验证；是否验证通过
 *
 * @author lihongmin
 * @date 2020/7/2 17:32
 * @since 1.0.0
 * @see CodeFactory#generateCode(CodeParam, DefaultCodeFactory.OrderType)
 * @see com.kevin.tool.order.code.check.Segment#check(CodeParam, Segment.STATUS)
 */
@Component
@AutoConfigureBefore(CheckCodeContext.class)
public class CodeApplicationContext extends CheckCodeContext implements CodeFactory {

    @Autowired
    private DefaultCodeFactory defaultCodeFactory;

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
