package com.biz.check.service;

import com.biz.check.constant.BusinessExceptionEnum;
import com.biz.check.constant.ThreadPoolEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.biz.check.constant.SelectByEnum;
import com.biz.check.dto.request.CheckRequestCreditDTO;
import com.biz.check.dto.request.CheckRequestDTO;
import com.biz.check.dto.request.RequestCreditDTO;
import com.biz.check.dto.response.CheckResponseDTO;
import com.biz.check.dto.response.CreditResultDTO;
import com.biz.check.manager.component.AbstractCheckService;
import com.biz.check.manager.component.ConfigServiceCache;
import com.biz.check.manager.component.CreditResultDTOFactory;
import com.quanyou.qup.middle.client.esb.sap.dto.CreditResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

import static com.biz.check.constant.BusinessExceptionEnum.buildException;
import static com.biz.check.constant.SelectByEnum.*;
import static com.quanyou.qup.middle.common.threadpool.ThreadPoolInit.THREAD_POOL_EXECUTOR_MAP;

/**
 *  信贷服务
 * @author kevin
 * @date 2020/8/13 11:35
 * @since 1.0.0
 */
@Slf4j
@Service
@AllArgsConstructor
@SuppressWarnings("unchecked")
public class CreditService extends AbstractCheckService {

    /**
     * 默认信贷范围： 货源安排使用
     */
    private static final String DEFAULT_CREDIT_ARRANGE = "6800";

    private final CreditRemoteService creditRemoteService;

    private final ConfigServiceCache configServiceCache;

    /** 需要查询【po占用量】的枚举类型 */
    private final Set<SelectByEnum> PO = Collections.unmodifiableSet(Sets.newHashSet(CREDIT_PO_VSO_FROZEN));
    /** 需要查询【vso占用量】的枚举类型 */
    private final Set<SelectByEnum> VSO = Collections.unmodifiableSet(Sets.newHashSet(CREDIT_VSO_FROZEN, CREDIT_PO_VSO_FROZEN));

    @Override
    protected CheckResponseDTO callService(CheckRequestDTO checkRequestDTO) {
        CheckRequestCreditDTO creditDTO = (CheckRequestCreditDTO)checkRequestDTO;
        CreditResultDTO creditResultDTO = creditTemplate(creditDTO);
        return new CheckResponseDTO(isPass(creditResultDTO), creditResultDTO);
    }

    @Override
    protected Boolean checkPass(CheckRequestDTO checkRequestDTO) {
        CheckRequestCreditDTO creditDTO = (CheckRequestCreditDTO)checkRequestDTO;
        CreditResultDTO creditResultDTO = creditTemplate(creditDTO);
        return isPass(creditResultDTO);
    }

    /**
     *  判断：是否验证通过
     * @param creditResultDTO 所有维度数据结果
     * @return 是否验证通过
     */
    private static Boolean isPass(CreditResultDTO creditResultDTO) {
        if (creditResultDTO == null || creditResultDTO.getMinusBalance() == null) {
            return Boolean.FALSE;
        }
        return creditResultDTO.getMinusBalance().compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * 适配查询信贷是否够用， 并且返回所有信息
     * @param creditDTO 信贷查询请求
     * @return 最新信贷信息，和计算结果
     */
    public CheckResponseDTO adaptCheck(RequestCreditDTO creditDTO) {
        return callService(new CheckRequestCreditDTO(creditDTO));
    }

    /**
     * 适配查询信贷是否够用
     * @param creditDTO 信贷查询请求
     * @return 是否验证通过
     */
    public Boolean adaptCheckPass(RequestCreditDTO creditDTO) {
        return checkPass(new CheckRequestCreditDTO(creditDTO));
    }


    /**
     * 查询信贷模板
     * 已经按照查询类型，将所有的类型都查询完成
     * @param checkRequestDTO 查询请求
     * @return 查询所有的结果数据, 不可能返回{@code null}
     */
    private CreditResultDTO creditTemplate(CheckRequestDTO checkRequestDTO) {
        final CheckRequestCreditDTO creditDTO = (CheckRequestCreditDTO)checkRequestDTO;
        ThreadPoolExecutor executor = THREAD_POOL_EXECUTOR_MAP.get(ThreadPoolEnum.CREDIT_TYPE_ITEM.name());

        List<CompletableFuture<Object>> futureList = Lists.newArrayList(
                CompletableFuture.supplyAsync(() -> {
                    String creditRange = StringUtils.isEmpty(creditDTO.getSaleOrgCode()) ? DEFAULT_CREDIT_ARRANGE : configServiceCache.getCreditScope(creditDTO);
                    log.error("信贷范围： {}", creditRange);
                    return creditRemoteService.getRemoteCredit(creditDTO.getAccount(), creditRange);
                }, executor));
        if (PO.contains(creditDTO.getSelectByEnum())) {
            futureList.add(CompletableFuture.supplyAsync(() -> creditRemoteService.getRemotePoCredit(creditDTO), executor));
        }
        if (VSO.contains(creditDTO.getSelectByEnum())) {
            futureList.add(CompletableFuture.supplyAsync(() -> creditRemoteService.getRemoteVsoCredit(creditDTO.getAccount()), executor));
        }
        // LockSupport.park
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0])).join();

        try {
            final Iterator<CompletableFuture<Object>> iterator = futureList.iterator();
            final CompletableFuture<Object> next = iterator.next();
            CreditResponseDTO remote = (CreditResponseDTO)next.get();
            CreditResultDTO creditResultDTO = CreditResultDTOFactory.cast(remote, creditDTO);
            if (PO.contains(creditDTO.getSelectByEnum())) {
                BigDecimal poAmount = (BigDecimal) iterator.next().get();
                creditResultDTO.setPoBalance(poAmount);
                creditResultDTO.subMinusBalance(poAmount);
            }
            if (VSO.contains(creditDTO.getSelectByEnum())) {
                BigDecimal vsoAmount = (BigDecimal) iterator.next().get();
                creditResultDTO.setVsoBalance(vsoAmount);
                creditResultDTO.subMinusBalance(vsoAmount);
            }
            return creditResultDTO;
        } catch (ExecutionException | InterruptedException e) {
            log.error("search credit error! " + e);
            BusinessExceptionEnum.buildException(BusinessExceptionEnum.SEARCH_EXCEPTION);
        }
        return null;
    }
}