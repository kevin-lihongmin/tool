package com.quanyou.qup.biz.check.feign;

import com.quanyou.qup.biz.check.feign.dto.response.CheckResponseDTO;
import com.quanyou.qup.biz.check.feign.fallback.RemoteSkuServiceFallbackImpl;
import com.quanyou.qup.core.bean.base.ResponseEntity;
import com.quanyou.qup.middle.common.dto.SkuInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 *  SKU 服务 Feign
 * @author kevin
 * @date 2020/8/10 13:14
 * @since 1.0.0
 */
@FeignClient(value = "biz-check-service",contextId = "remoteBizCheckSkuService", fallback = RemoteSkuServiceFallbackImpl.class)
public interface RemoteSkuService {

    /**
     *  获取Sku明细信息
     * @param skuCodeList 检查的sku、sku组合、单包件列表
     * @return Sku明细信息和检查结果
     */
    @PostMapping("/provider/v1/sku/getSkuAndCheck")
    ResponseEntity<CheckResponseDTO> getSkuAndCheck(@RequestBody List<SkuInfoDTO> skuCodeList);

    /**
     *  验证Sku信息
     * @param skuCodeList 检查的sku、sku组合、单包件列表
     * @return Sku状态是否正常
     */
    @PostMapping("/provider/v1/sku/checkSku")
    ResponseEntity<Boolean> checkSku(@RequestBody List<SkuInfoDTO> skuCodeList);

}