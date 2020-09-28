package com.common.dto;


import com.quanyou.qup.middle.common.enums.SkuTypeEnum;

/**
 *  sku信息
 *
 * @author kevin
 * @date 2020/8/26 9:19
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public final class SkuInfoDTO {

    /**
     * sku层的类型：sku、sku组合、产品或单包件
     */
    public final SkuTypeEnum typeEnum;

    /**
     * 对应的编码
     */
    public final String code;

    public SkuInfoDTO(SkuTypeEnum typeEnum, String code) {
        this.typeEnum = typeEnum;
        this.code = code;
    }

    /**
     * 无参数构造， 反序列化使用
     */
    public SkuInfoDTO() {
        this.typeEnum = null;
        this.code = null;
    }

}