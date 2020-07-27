package com.kevin.tool.order.code;

import lombok.Builder;
import lombok.Data;

/**
 *  是否检查列表
 *
 *  <p> {@link #isPassCheck} 为检查后的结果， 前体是需要检查才会去检查
 *
 *  <p> 其余为对应的是否检查标识配置
 *
 * @author lihongmin
 * @date 2020/7/21 16:15
 * @since 1.0.0
 */
@Data
@Builder
public class CheckDTO {

    /**
     *  是否检查通过
     */
    private Boolean isPassCheck;

    /**
     * 是否允销检查
     */
     private Boolean isAllowCheck;

    /**
     * 是否信贷检查
     */
    private Boolean isCreditCheck;

    /**
     *  是否经销商冻结
     */
    private Boolean isFreezeCustomer;

    /**
     *  是否业务账号冻结
     */
    private Boolean isFreezeAccount;

    /**
     *  是否冻结送达方
     */
    private Boolean isFreezeAddress;

    /**
     * 产品状态检查
     */
    private Boolean isProductCheck;

    /**
     *  是否自动审核
     */
    private Boolean isAutoAudit;

}
