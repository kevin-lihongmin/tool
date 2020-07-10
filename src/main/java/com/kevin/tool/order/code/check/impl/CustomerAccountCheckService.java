package com.kevin.tool.order.code.check.impl;

import com.kevin.tool.order.code.check.CheckRequestContext;
import com.kevin.tool.order.code.check.CheckService;
import com.kevin.tool.order.code.check.RequestContextParam;
import com.kevin.tool.order.code.generate.param.CodeParam;
import com.kevin.tool.order.code.generate.param.SaleCodeParam;
import org.springframework.stereotype.Service;
import sun.misc.Request;

import java.util.Random;

/**
 *  经销商业务账号检查
 *
 * @author lihongmin
 * @date 2020/6/30 16:31
 * @since 1.0.0
 */
@Service
public class CustomerAccountCheckService implements CheckService {

    @Override
    public Boolean isCheck() {
        // 特殊字段的获取
        SaleCodeParam saleCodeParam = (SaleCodeParam)CheckRequestContext.getInstance().get().codeParam;
        System.out.println(saleCodeParam);
        return new Random().nextBoolean();
    }
}
