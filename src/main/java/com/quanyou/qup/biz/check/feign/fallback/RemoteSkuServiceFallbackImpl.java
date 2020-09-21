package com.quanyou.qup.biz.check.feign.fallback;

import com.quanyou.qup.biz.check.feign.RemoteSkuService;
import com.quanyou.qup.biz.check.feign.dto.response.CheckResponseDTO;
import com.quanyou.qup.core.bean.base.ResponseEntity;
import com.quanyou.qup.middle.common.dto.SkuInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *  sku检查，熔断降级处理
 * @author kevin
 * @date 2020/8/10 13:09
 * @since 1.0.0
 */
@Slf4j
@Component
public class RemoteSkuServiceFallbackImpl implements RemoteSkuService {


    @Override
    public ResponseEntity<CheckResponseDTO> getSkuAndCheck(List<SkuInfoDTO> skuCodeList) {
        return ResponseEntity.error("search error！");
    }

    @Override
    public ResponseEntity<Boolean> checkSku(List<SkuInfoDTO> skuCodeList) {
        return ResponseEntity.error("search error！");
    }

}
