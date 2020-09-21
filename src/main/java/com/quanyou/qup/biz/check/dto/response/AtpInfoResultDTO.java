package com.quanyou.qup.biz.check.dto.response;

import com.quanyou.qup.biz.check.dto.request.AtpInfoDTO;
import com.quanyou.qup.middle.common.enums.SkuTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 *  atp 可用来查询服务 请求
 *
 * @author kevin
 * @date 2020/8/30 16:09
 * @since 1.0.0
 */
@Data
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

    public AtpInfoResultDTO(AtpInfoDTO atpInfoDTO, Integer availableQuantity) {
        this.typeEnum = atpInfoDTO.typeEnum;
        this.code = atpInfoDTO.code;
        this.quantity = atpInfoDTO.bigDecimal;
        this.availableQuantity = availableQuantity;
    }

    /**
     * 为组合下的sku专门设置数量
     * @param skuCode sku编码
     * @param availableQuantity 可满足的sku量
     */
    public AtpInfoResultDTO(String skuCode, Integer availableQuantity) {
        this.typeEnum = SkuTypeEnum.SKU;
        this.code = skuCode;
        this.quantity = BigDecimal.ZERO;
        this.availableQuantity = availableQuantity;
    }

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
