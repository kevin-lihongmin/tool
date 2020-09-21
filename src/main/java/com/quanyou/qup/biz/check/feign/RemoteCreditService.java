package com.quanyou.qup.biz.check.feign;

import com.quanyou.qup.biz.check.feign.dto.request.RequestCreditDTO;
import com.quanyou.qup.biz.check.feign.dto.response.CheckResponseDTO;
import com.quanyou.qup.biz.check.feign.fallback.RemoteCreditServiceFallbackImpl;
import com.quanyou.qup.core.bean.base.ResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *  信贷 服务 Feign
 * @author kevin
 * @date 2020/8/10 13:14
 * @since 1.0.0
 */
@FeignClient(value = "biz-check-service",contextId = "remoteBizCheckCreditService", fallback = RemoteCreditServiceFallbackImpl.class)
public interface RemoteCreditService {

    /**
     *  获取信贷明细信息
     * @param creditDTO
     * @return 信贷明细信息
     */
    @PostMapping("/provider/v1/credit/getCreditAndCheck")
    ResponseEntity<CheckResponseDTO> getCreditAndCheck(@RequestBody RequestCreditDTO creditDTO);

    /**
     *  验证信贷信息
     * @param creditDTO 信贷请求
     * @return 信贷验证是否通过
     */
    @PostMapping("/provider/v1/credit/checkCredit")
    ResponseEntity<Boolean> checkCredit(@RequestBody RequestCreditDTO creditDTO);

}