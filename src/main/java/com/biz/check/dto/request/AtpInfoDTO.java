package com.biz.check.dto.request;

import com.common.enums.SkuTypeEnum;
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
public final class AtpInfoDTO {

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
    public final BigDecimal bigDecimal;

    public AtpInfoDTO() {
        this.typeEnum = null;
        this.code = null;
        this.bigDecimal = null;
    }
}
