package com.kevin.tool.order.code;

import com.kevin.tool.order.code.check.CheckCodeContext;
import com.kevin.tool.order.code.check.CheckService;
import com.kevin.tool.order.code.check.PreposingStateConfig;
import com.kevin.tool.order.code.check.marker.DefaultMarkerFlagService;
import com.kevin.tool.order.code.check.marker.MarkerCheckService;
import com.kevin.tool.order.code.check.marker.MarkerFlagService;
import com.kevin.tool.order.code.generate.CodeFactory;
import com.kevin.tool.order.code.generate.DefaultCodeFactory;
import com.kevin.tool.order.code.generate.param.CodeParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 *  订单码业务处理容器
 *
 *  <p>
 *      1、订单码生成
 *      2、不同订单类型（采购订单、销售订单），在某个订单节点（比如：下单节点的验证）的检查验证；是否验证通过
 *      3、返回订单码对应标识值（Boolean类型），是否自动转单、发送Tms等动作交给客户端发起
 *      4、检查和返回是否检查标识
 *  </p>
 *
 *  <p> 4、中对应的是在采购和订单服务中生成订单码之前就需要检查的项目，返回是否自动审核等标识
 *
 * @author lihongmin
 * @date 2020/7/2 17:32
 * @since 1.0.0
 * @see #generateCode(CodeParam)
 * @see #generateCode(CodeParam, OrderType) 订单码生成
 * @see #check(CodeParam, SegmentState) 每个节点是否检查通过
 *
 * @see #isPurchaseControl(String) 是否采购控制
 * @see #isAutoOrder(String) 是否自动开单
 * @see #isVsoControl(String) 是否可转{@code VSO}
 * @see #isSingleOrderControl(String) 是否整单开单控制
 * @see #isSoControl(String) 开单SO控制
 * @see #isTms(String) 是否发送Tms标识
 * @see #saleCreateFlag(String) 销售开单开关集合
 * @see #isSaleShortage(String) [销售开单]是否紧缺
 * @see #isVso2SoShortage(String) [VSO转SO]是否紧缺
 *
 * @see #chaseCheckAndFlag(CodeParam, PreposingState) 采购订单是否检查通过，并且返回是否自动审核等标识
 * @see #saleCheckAndFlag(CodeParam, PreposingState) 销售订单是否检查通过，并且返回是否自动审核等标识
 */
@Component
@AutoConfigureBefore(CheckCodeContext.class)
public class CodeApplicationContextImpl extends CheckCodeContext implements MarkerCheckService, CodeFactory {

    private final DefaultCodeFactory defaultCodeFactory;

    private static final MarkerFlagService markerFlagService = new DefaultMarkerFlagService();

    /**
     *  节点服务（责任）链
     * @see Collections.UnmodifiableMap
     */
    @SuppressWarnings("JavadocReference")
    private static Map<PreposingState, List<CheckService>> CONFIG_SERVICE_MAP;

    @Autowired
    public CodeApplicationContextImpl(DefaultCodeFactory defaultCodeFactory) {
        this.defaultCodeFactory = defaultCodeFactory;
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();

        PreposingState[] states = PreposingState.values();
        Map<PreposingState, List<CheckService>> configMap = new HashMap<>(2);

        for (PreposingState preposingState : states) {
            PreposingStateConfig status = PreposingStateConfig.getStatus(preposingState);

            if (!status.getCheckList().isEmpty()) {
                List<CheckService> checkServices = new ArrayList<>();
                status.getCheckList().forEach(clazz -> {
                    CheckService bean = beanFactory.getBean(clazz);
                    checkServices.add(bean);
                    checkServiceSet.add(bean);
                });
                configMap.put(preposingState, checkServices);
            }
        }

        CONFIG_SERVICE_MAP = Collections.unmodifiableMap(configMap);
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
    public Boolean isAutoOrder(String code) {
        return markerFlagService.isAutoOrder(code);
    }

    @Override
    public Boolean isTms(String code) {
        return markerFlagService.isTms(code);
    }

    @Override
    public Boolean isPurchaseControl(String code) {
        return markerFlagService.isPurchaseControl(code);
    }

    @Override
    public CheckDTO chaseCheckAndFlag(CodeParam codeParam, PreposingState preposingState) {
        // 查询采购订单配置服务
        Boolean[] booleans = new Boolean[0];

        Boolean aBoolean = invokeService(booleans, preposingState);
        return CheckDTO.builder()
                .isPassCheck(aBoolean)
                // 其他检查服务回执
                .build();
    }

    @Override
    public CheckDTO saleCheckAndFlag(CodeParam codeParam, PreposingState preposingState) {
        // 查询销售订单配置服务
        Boolean[] booleans = new Boolean[0];
        Boolean aBoolean = invokeService(booleans, preposingState);
        return CheckDTO
                .builder()
                .isPassCheck(aBoolean)
                // 其他检查服务回执
                .build();
    }

    @Override
    public Boolean isVsoControl(String code) {
        return markerFlagService.isVsoControl(code);
    }

    @Override
    public Boolean isSingleOrderControl(String code) {
        return markerFlagService.isSingleOrderControl(code);
    }

    @Override
    public Boolean isAtp(String code) {
        return markerFlagService.isAtp(code);
    }

    @Override
    public Boolean isCheckPreSell(String code) {
        return markerFlagService.isCheckPreSell(code);
    }

    @Override
    public Boolean isSoControl(String code) {
        return markerFlagService.isSoControl(code);
    }

    @Override
    public SaleCreateDTO saleCreateFlag(String code) {
        return markerFlagService.saleCreateFlag(code);
    }

    @Override
    public Boolean isSaleShortage(String code) {
        return markerFlagService.isSaleShortage(code);
    }

    @Override
    public Boolean isVso2SoShortage(String code) {
        return markerFlagService.isVso2SoShortage(code);
    }

    /**
     *  调用检查服务
     * @param checkList 是否检查的标识列表
     * @param preposingState 检查阶段
     * @return 是否检查通过
     */
    private Boolean invokeService(Boolean[] checkList, PreposingState preposingState) {
        int i = 0;
        List<CheckService> checkServices = CONFIG_SERVICE_MAP.get(preposingState);
        for (CheckService checkService : checkServices) {
            if (checkList[i] && !checkService.isCheck()) {
                return false;
            }
            i++;
        }
        return true;
    }

}
