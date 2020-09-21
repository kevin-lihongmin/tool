package com.quanyou.qup.biz.check.dto.response;

import com.quanyou.qup.biz.check.constant.CreditSelectByEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *  信贷 调用结果明细
 * @author kevin
 * @date 2020/8/13 9:24
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CreditResultDTO extends ResultDTO implements Serializable, Cloneable {

    /**
     *  序列化标识
     */
    private static final long serialVersionUID = 6918450910992152773L;

    /**
     *  业务账号
     */
    private String account;

    /**
     * 需要花费金额
     */
    private BigDecimal costAmount;

    /**
     * 减去之后的最后金额
     * @see CreditSelectByEnum#CREDIT_SIMPLE {@link #minusBalance} = {@link #balance} - {@link #costAmount}
     * @see CreditSelectByEnum#CREDIT_VSO_FROZEN {@link #minusBalance} = {@link #balance} - {@link #vsoFrozenBalance} - {@link #vsoBalance} - {@link #costAmount}
     * @see CreditSelectByEnum#CREDIT_PO_VSO_FROZEN {@link #minusBalance} = {@link #balance} - {@link #poBalance} - {@link #vsoFrozenBalance} - {@link #vsoBalance} - {@link #costAmount}
     */
    private BigDecimal minusBalance;

    /**
     * po占用量【已提交，但是还没有完成】
     */
    private BigDecimal poBalance;

    /**
     * VSO占用金额
     */
    private BigDecimal vsoBalance;

    /**
     * VSO货源调配冻结金额
     */
    private BigDecimal vsoFrozenBalance;

    /**
     * 信贷余额
     */
    private BigDecimal balance;

    /**
     * 销售组织
     */
    private String saleOrgCode;

    /**
     * 销售渠道
     */
    private String saleChannelCode;

    /**
     * 产品
     */
    private String product;

    /**
     * 信用风险等级
     */
    private String creditLevel;

    /**
     * 是否检查信贷, 查询为空，也识别为{@link Boolean#FALSE}
     */
    private Boolean creditFlag;

    @Override
    public CreditResultDTO clone() {
        try {
            return (CreditResultDTO)super.clone();
        } catch (CloneNotSupportedException e) {
            return new CreditResultDTO();
        }
    }

    /**
     * 计算当前余额
     * @param subAmount 需要减去的占用额度
     * @see #costAmount
     * @see #poBalance
     * @see #vsoBalance
     * @see #vsoFrozenBalance
     */
    public void subMinusBalance(BigDecimal subAmount) {
        minusBalance = minusBalance.subtract(subAmount);
    }
}