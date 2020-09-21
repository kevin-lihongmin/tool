package com.quanyou.qup.biz.check.feign.dto.request;

import com.quanyou.qup.biz.check.feign.dto.enums.CreditSelectByEnum;
import lombok.Builder;
import lombok.Getter;
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
@Builder
public class RequestCreditDTO {

    /**
     *  查询方式
     */
    private CreditSelectByEnum creditSelectByEnum;

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
     * @param account 业务账号
     * @param creditRange 信贷范围， 需要先查询销售范围才能确定
     * @param costAmount 当前订单等花费的金额， 如果不花费金额可以只传{@code null}或者[零]
     */
    /*public RequestCreditDTO(String account, String creditRange, BigDecimal costAmount) {
        this.account = account;
        this.creditRange = creditRange;
        this.costAmount = costAmount;
    }*/

}
