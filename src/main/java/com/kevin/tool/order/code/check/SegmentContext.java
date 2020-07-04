package com.kevin.tool.order.code.check;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  分段配置
 * @author lihongmin
 * @date 2020/7/1 10:50
 * @since 1.0.0
 */
public abstract class SegmentContext implements Segment {

    protected static Set<STATUS> index;

    protected static Map<STATUS, List<CheckService>> MAP_SERVICE;

    static {
        Set<STATUS> map = new HashSet<>();
        for (STATUS value : STATUS.values()) {
            index.add(value);
        }
        index = Collections.unmodifiableSet(map);

        MAP_SERVICE = new ConcurrentHashMap<STATUS, LinkedHashSet<CheckService>>();
        MAP_SERVICE.put(STATUS.CREATE_ORDER, Lists.newArrayList(UserCheckService.class));

        MAP_SERVICE.put(STATUS.CREATE_PURCHASE_ORDER, Lists..newArrayList(UserCheckService.class));


    }

    /**
     *  段位置信息
     */
    @Data
    class Entry{
        private int start;
        private int end;
    }

    @Override
    public String getSegment() {
        RequestContextParam param = CheckRequestContext.getInstance().get();
        return param.codeParam.getOrderCode().substring(param.status.getStart(), param.status.getEnd());
    }
}
