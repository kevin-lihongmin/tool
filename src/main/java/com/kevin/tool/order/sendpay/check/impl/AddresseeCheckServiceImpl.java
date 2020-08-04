package com.kevin.tool.order.sendpay.check.impl;

import com.kevin.tool.order.sendpay.check.CheckRequestContext;
import com.kevin.tool.order.sendpay.check.CheckService;
import com.kevin.tool.order.sendpay.generate.param.SaleCodeParam;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 *  送达方检查
 *
 * @author lihongmin
 * @date 2020/6/30 16:31
 * @since 1.0.0
 */
@Service
public class AddresseeCheckServiceImpl implements CheckService {

    @Override
    public Boolean isCheck() {
        // 特殊字段的获取
        SaleCodeParam saleCodeParam = (SaleCodeParam)CheckRequestContext.getInstance().get().codeParam;
        System.out.println(saleCodeParam);
        return new Random().nextBoolean();
    }
}
