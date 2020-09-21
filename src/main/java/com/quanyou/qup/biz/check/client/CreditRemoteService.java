package com.quanyou.qup.biz.check.client;

import com.google.common.collect.Lists;
import com.quanyou.qup.biz.check.dto.request.CheckRequestCreditDTO;
import com.quanyou.qup.core.bean.base.ResponseEntity;
import com.quanyou.qup.core.collection.CollectionUtil;
import com.quanyou.qup.middle.client.esb.sap.SapClient;
import com.quanyou.qup.middle.client.esb.sap.dto.CreditRequestDTO;
import com.quanyou.qup.middle.client.esb.sap.dto.CreditResponseDTO;
import com.quanyou.qup.so.center.feign.RemoteVsoCreditService;
import com.quanyou.qup.so.center.feign.dto.RemoteResponseVsoCreditDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 *  远程：信贷查询， 使用 webservice调用
 * @author kevin
 * @date 2020/8/13 10:53
 * @since 1.0.0
 */
@Repository
@AllArgsConstructor
public class CreditRemoteService {

    /**
     * httpClient 查询SAP信贷
     */
    private final SapClient sapClient;

    /**
     *   VSO 占用信贷
     */
    private final RemoteVsoCreditService vsoCreditService;

    /**
     * 查询信贷信息
     * @param account 业务账号
     * @param creditRange 信贷范围
     * @return 信贷余额和等级
     */
    public CreditResponseDTO getRemoteCredit(String account, String creditRange) {
        return sapClient.getCreditBalanceAndLevel(new CreditRequestDTO(account, creditRange));
    }

    /**
     *  查询VSO占用的信贷
     * @param account 业务账号 可变数组
     * @return 信贷列表信息
     */
    public List<RemoteResponseVsoCreditDTO> getRemoteVsoCreditList(String... account) {
        ResponseEntity<List<RemoteResponseVsoCreditDTO>> response = vsoCreditService.queryCreditByAccountCodes(Lists.newArrayList(account));
        if (!response.isSuccess() || response.getData() == null) {
            return null;
        }
        return response.getData();
    }

    /**
     *  查询VSO占用的信贷
     * @param account 业务账号 可变数组
     * @return 信贷列表信息
     */
    public BigDecimal getRemoteVsoCredit(String account) {
        List<RemoteResponseVsoCreditDTO> remoteVsoCreditList = getRemoteVsoCreditList(account);
        if (CollectionUtil.isEmpty(remoteVsoCreditList)) {
            return new BigDecimal(BigInteger.ZERO);
        }
        RemoteResponseVsoCreditDTO remoteResponseVsoCreditDTO = remoteVsoCreditList.get(0);
        if (remoteResponseVsoCreditDTO == null) {
            return new BigDecimal(BigInteger.ZERO);
        }
        return remoteResponseVsoCreditDTO.getCreditAmount();
    }

    /**
     *  po占用量： 已提交并且未完成的采购单 TODO 待长老提供接口
     * @param creditDTO 业务账号
     * @param <U> 待确定类型
     * @return 待确定类型
     */
    public <U> U getRemotePoCredit(CheckRequestCreditDTO creditDTO) {
        return null;
    }
}
