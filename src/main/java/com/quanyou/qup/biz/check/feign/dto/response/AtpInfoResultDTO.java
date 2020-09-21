package com.quanyou.qup.biz.check.feign.dto.response;

import com.quanyou.qup.middle.common.enums.SkuTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 *  atp 可用来查询服务 请求
 *
 * @author kevin
 * @date 2020/8/30 16:09
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public final class AtpInfoResultDTO {

    /**
     * sku层的类型：sku、sku组合、产品或单包件
     */
    public final SkuTypeEnum typeEnum;

    /**
     * 对应的编码
     */
    public final String code;

    /**
     * 需求量
     */
    public final BigDecimal quantity;

    /**
     * 真实可用量
     */
    public Integer availableQuantity;

    /**
     * 反序列化使用
     */
    public AtpInfoResultDTO() {
        this.typeEnum = null;
        this.code = null;
        this.quantity = null;
        this.availableQuantity = null;
    }
}
