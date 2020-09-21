package com.quanyou.qup.biz.check.manager.sku;

import com.quanyou.qup.middle.common.dto.SkuInfoDTO;
import com.quanyou.qup.middle.common.enums.SkuTypeEnum;

import java.util.List;
import java.util.Map;

/**
 *  检查sku、sku组合、单包件等 状态
 * @author kevin
 * @date 2020/8/26 9:45
 * @since 1.0.0
 */
public interface CheckSkuInfo {


    void checkChain(Map<SkuTypeEnum, List<SkuInfoDTO>> groupSku);

}
