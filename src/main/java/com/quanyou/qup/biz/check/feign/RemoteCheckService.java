package com.quanyou.qup.biz.check.feign;

import com.quanyou.qup.biz.check.feign.dto.request.CheckChainDTO;
import com.quanyou.qup.biz.check.feign.dto.response.CheckResultDTO;
import com.quanyou.qup.biz.check.feign.fallback.RemoteCheckServiceFallbackImpl;
import com.quanyou.qup.core.bean.base.ResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *  检查链路 服务 Feign
 * @author kevin
 * @date 2020/8/10 13:14
 * @since 1.0.0
 */
@FeignClient(value = "biz-check-service",contextId = "remoteBizCheckService", fallback = RemoteCheckServiceFallbackImpl.class)
public interface RemoteCheckService {

    @PostMapping("/provider/v1/check/checkChain")
    ResponseEntity<CheckResultDTO> checkChain(@RequestBody CheckChainDTO... checkChains);

}