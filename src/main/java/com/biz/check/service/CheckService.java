package com.biz.check.service;

import com.alibaba.fastjson.JSON;
import com.biz.check.constant.CheckTypeEnum;
import com.biz.check.constant.ThreadPoolEnum;
import com.biz.check.dto.request.CheckChainDTO;
import com.biz.check.dto.response.CheckResponseDTO;
import com.biz.check.dto.response.CheckResultDTO;
import com.biz.check.manager.component.AbstractCheckService;
import com.quanyou.qup.middle.common.performance.TimeConsume;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.quanyou.qup.middle.common.threadpool.SimpleThreadPool.executeAll;

/**
 *  检查服务
 *
 * @author kevin
 * @date 2020/8/12 13:04
 * @since 1.0.0
 */
@Slf4j
@Service
public class CheckService implements ApplicationContextAware {

    /**
     * 检查服务策略枚举
     */
    protected static Map<CheckTypeEnum, AbstractCheckService> strategy = new ConcurrentHashMap<>(16);

    /**
     *  Spring IOC容器
     */
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     *  执行方法的调用
     * @param checkChainDTO 公共顶层请求参数
     * @return 公共顶层结果
     */
    public CheckResponseDTO invokeStrategy(CheckChainDTO checkChainDTO) {
        return strategy.get(checkChainDTO.getCheckTypeEnum()).check(checkChainDTO);
    }

    /**
     *  在自定义初始化的生命周期中，初始化策略关系
     * @see AbstractAutowireCapableBeanFactory {@code invokeInitMethods}
     */
    @PostConstruct
    public void initStrategy() {
        BeanFactory beanFactory = applicationContext;
        strategy.put(CheckTypeEnum.CREDIT, beanFactory.getBean(CreditService.class));
        strategy.put(CheckTypeEnum.CUSTOMER, beanFactory.getBean(CustomerService.class));
        strategy.put(CheckTypeEnum.ACCOUNT, beanFactory.getBean(AccountService.class));
        strategy.put(CheckTypeEnum.ADDRESSEE, beanFactory.getBean(AddresseeService.class));
        strategy.put(CheckTypeEnum.SKU, beanFactory.getBean(SkuService.class));
        strategy.put(CheckTypeEnum.ATP, beanFactory.getBean(AtpService.class));
        strategy.put(CheckTypeEnum.ALLOW, beanFactory.getBean(AllowService.class));
        // Immutable 模式
        strategy = Collections.unmodifiableMap(strategy);
    }

    /**
     *  检查请求列表
     * @param checkChains 检查项
     * @return 检查结果
     */
    @TimeConsume
    public CheckResultDTO checkChainProcess(CheckChainDTO... checkChains) {
        List<Callable<CheckResponseDTO>> taskList = new ArrayList<>(checkChains.length);
        for (CheckChainDTO checkDTO : checkChains) {
            taskList.add(() -> invokeStrategy(checkDTO));
        }
        // 并行执行任务，阻塞获取结果
        List<Future<CheckResponseDTO>> futures = executeAll(ThreadPoolEnum.CHECK_CHAIN_LIST.name(), taskList);

        final CheckResultDTO result = new CheckResultDTO(Boolean.TRUE);
        futures.forEach(checkResponse -> {
            try {
                CheckResponseDTO checkResponseDTO = checkResponse.get();
                log.error("checkResponseDTO = {}", JSON.toJSONString(checkResponseDTO));
                result.addResponse(checkResponseDTO);
                if (checkResponseDTO == null || !checkResponseDTO.getIsPass()) {
                    result.setIsTotalPass(Boolean.FALSE);
                }
            } catch (InterruptedException | ExecutionException e) {
                log.error("check list getFuture error:" + e);
                result.addResponse(null);
                result.setIsTotalPass(Boolean.FALSE);
            }
        });
        return result;
    }

}
