package com.quanyou.qup.biz.check.feign.dto.enums;


import com.quanyou.qup.biz.check.feign.dto.request.CheckRequestAtpDTO;
import com.quanyou.qup.biz.check.feign.dto.request.CheckRequestCreditDTO;
import com.quanyou.qup.biz.check.feign.dto.request.CheckRequestDTO;
import com.quanyou.qup.biz.check.feign.dto.response.*;

/**
 *  检查类型枚举
 * @author kevin
 * @date 2020/8/12 10:10
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public enum CheckTypeEnum {

    /**
     * 允销检查
     */
    ALLOW(CheckRequestDTO.class, ResultDTO.class),

    /**
     * 信贷检查
     */
    CREDIT(CheckRequestCreditDTO.class, CreditResultDTO.class),

    /**
     * 经销商
     */
    CUSTOMER(CheckRequestDTO.class, CustomerResultDTO.class),

    /**
     * 业务账号
     */
    ACCOUNT(CheckRequestDTO.class, AccountResultDTO.class),

    /**
     * 送达方
     */
    ADDRESSEE(CheckRequestDTO.class, AddresseeResultDTO.class),

    /**
     * 送达方
     */
    ATP(CheckRequestAtpDTO.class, AtpResultDTO.class),

    /**
     * SKU检查
     */
    SKU(CheckRequestCreditDTO.class, SkuResultDTO.class);

    /**
     * 请求入参类型
     */
    public final Class<? extends CheckRequestDTO> clazz;

    /**
     * 返回值类型
     */
    public final Class<? extends ResultDTO> resultClazz;

    CheckTypeEnum(Class<? extends CheckRequestDTO> clazz, Class<? extends ResultDTO> resultClazz) {
        this.clazz = clazz;
        this.resultClazz = resultClazz;
    }
}
