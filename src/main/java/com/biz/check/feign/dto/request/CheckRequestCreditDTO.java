package com.biz.check.feign.dto.request;

import com.biz.check.feign.dto.enums.SelectByEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 *  信贷检查
 *
 * @author kevin
 * @date 2020/8/12 11:04
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
public class CheckRequestCreditDTO extends CheckRequestDTO {

    /**
     *  业务账号
     */
    private String account;

    /**
     * 需要花费金额
     */
    private BigDecimal costAmount;

    /*------------------------- 销售范围，用于查询信贷范围 ----------------------*/
    /**
     * 销售组织编码（销售范围）
     */
    private String saleOrgCode;

    /**
     * 销售渠道编码（销售范围）
     */
    private String saleChannelCode;

    /**
     * 产品组编码（销售范围）
     */
    private String productGroupCode;

    /**
     *  适配信贷查询
     * @param creditDTO 单个信贷查询
     */
    public CheckRequestCreditDTO(RequestCreditDTO creditDTO) {
        super(null, SelectByEnum.valueOf(creditDTO.getCreditSelectByEnum() + ""));
        this.account = creditDTO.getAccount();
        this.saleOrgCode = creditDTO.getSaleOrgCode();
        this.saleChannelCode = creditDTO.getSaleChannelCode();
        this.productGroupCode = creditDTO.getProductGroupCode();
        this.costAmount = creditDTO.getCostAmount();
    }
}
