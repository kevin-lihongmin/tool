package com.kevin.tool.order.code.generate.impl;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  模拟用户任务
 * @author lihongmin
 * @date 2020/6/30 16:31
 * @since 1.0.0
 */
@Service
public class CachePurchaseDefinitionService extends PurchaseDefinitionService implements SegmentCodeImpl {

    private PurchaseDefinitionService delegate;

    private Map<String, String> cache = new ConcurrentHashMap<>();

    @Override
    public String configCode() {
        return "01";
    }

    public void setDelegate(PurchaseDefinitionService delegate) {
        this.delegate = delegate;
    }
}
