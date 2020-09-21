package com.quanyou.qup.biz.check.feign.dto.request;

import com.quanyou.qup.middle.common.dto.SkuInfoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 *  Sku 检查
 *
 * @author kevin
 * @date 2020/8/12 11:04
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
public class CheckRequestSkuDTO extends CheckRequestDTO {

    /**
     *  sku编码集合
     */
    private List<SkuInfoDTO> skuCodeList;

    /**
     * 构造函数
     * @param skuCodeList 需要查询允销的商品列表
     */
    public CheckRequestSkuDTO(List<SkuInfoDTO> skuCodeList) {
        super(null, null);
        this.skuCodeList = skuCodeList;
    }

}
