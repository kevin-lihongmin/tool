package com.kevin.tool.order.code.generate.config;

import com.kevin.tool.async.SimpleThreadPool;
import com.kevin.tool.order.code.generate.impl.UserConfigService;
import com.kevin.tool.order.code.generate.param.CodeParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import static com.kevin.tool.async.SimpleThreadPool.ThreadPoolEnum.CREATE_ORDER;

/**
 *  采购订单配置服务
 *
 * @author lihongmin
 * @date 2020/7/1 10:07
 * @since 1.0.0
 */
@Service
public class PurchaseConfigService implements SegmentCode {

    /**
     * 总任务数
     */
    private static final int TASK = 5;

    @Autowired
    private UserConfigService userConfigService;

    @Override
    public String configCode(CodeParam codeParam) {
        final StringBuilder purchase = new StringBuilder();
        ArrayList<Callable<String>> taskList = new ArrayList<>(TASK);
        taskList.add(() -> {
            purchase.replace(0, 1, userConfigService.checkTask());
            return null;
        });
        taskList.add(() -> {
            purchase.replace(2, 3, userConfigService.checkTask());
            return null;
        });

        SimpleThreadPool.executeAll(CREATE_ORDER, taskList);
//        SimpleThreadPool.executeRunnable(CREATE_ORDER, taskList.toArray(new Runnable[taskList.size()]));

        return purchase.toString();
    }
}
