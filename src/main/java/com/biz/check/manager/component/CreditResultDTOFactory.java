package com.biz.check.manager.component;

import com.biz.check.dto.request.CheckRequestCreditDTO;
import com.biz.check.dto.response.CreditResultDTO;
import com.quanyou.qup.middle.client.esb.sap.dto.CreditResponseDTO;
import com.quanyou.qup.middle.common.enums.BooleanEnum;

import java.math.BigDecimal;

/**
 *  使用原型模式（深拷贝），进行赋值，初始化返回对象
 * @author kevin
 * @date 2020/8/13 13:08
 * @since 1.0.0
 */
public class CreditResultDTOFactory {

    /**
     * 原型对象
     */
    private static volatile CreditResultDTO creditResultDTO;

    /**
     * 构造私有化
     */
    private CreditResultDTOFactory() {
    }

    /**
     * 双重锁方式获取初始化对象
     * @return 全为空的 信贷对象
     */
    private static CreditResultDTO get() {
        if (creditResultDTO == null) {
            synchronized (CreditResultDTOFactory.class) {
                if (creditResultDTO == null) {
                    creditResultDTO = new CreditResultDTO();
                    creditResultDTO.setBalance(BigDecimal.ZERO);
                    creditResultDTO.setCostAmount(BigDecimal.ZERO);
                    creditResultDTO.setPoBalance(BigDecimal.ZERO);
                    creditResultDTO.setVsoBalance(BigDecimal.ZERO);
                    creditResultDTO.setVsoFrozenBalance(BigDecimal.ZERO);
                }
            }
        }
        return creditResultDTO;
    }

    /**
     *  转换信贷查询结果
     * @param remote 当前sap信贷信息
     * @param creditDTO 信贷请求参数
     * @return 信贷查询结果
     */
    public static CreditResultDTO cast(CreditResponseDTO remote, CheckRequestCreditDTO creditDTO) {
        CreditResultDTO creditResultDTO = get().clone();
        creditResultDTO.setAccount(creditDTO.getAccount());
        creditResultDTO.setCostAmount(creditDTO.getCostAmount());

        BigDecimal currentSap = new BigDecimal(remote.getBalance());
        creditResultDTO.setBalance(currentSap);
        creditResultDTO.setMinusBalance(currentSap);
        creditResultDTO.setSaleOrgCode(remote.getSaleOrgCode());
        creditResultDTO.setSaleChannelCode(remote.getSaleChannelCode());
        creditResultDTO.setProduct(remote.getProduct());
        creditResultDTO.setCreditLevel(remote.getCreditLevel());
        creditResultDTO.setCreditFlag(BooleanEnum.parse(remote.getCreditFlag()));

        // 计算差额
        if (creditDTO.getCostAmount() != null) {
            creditResultDTO.subMinusBalance(creditDTO.getCostAmount());
        }
        return creditResultDTO;
    }

}
