package com.kevin.tool.order.code.generate.impl;

import com.kevin.tool.order.code.check.RequestContextParam;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  缓存服务， 包装{@link SegmentCodeImpl}
 * @author lihongmin
 * @date 2020/6/30 16:31
 * @since 1.0.0
 */
@Service
public class CacheSegmentCodeImpl implements SegmentCodeImpl {

    private SegmentCodeImpl delegate;

    private Map<String, String> cache = new ConcurrentHashMap<>();

    @Override
    public String configCode(RequestContextParam param) {
        return "01";
    }

    public void setDelegate(PurchaseDefinitionService delegate) {
        this.delegate = delegate;
    }
}
