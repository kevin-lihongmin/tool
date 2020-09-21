package com.quanyou.qup.biz.check.feign.fallback;

import com.quanyou.qup.biz.check.feign.RemoteCreditService;
import com.quanyou.qup.biz.check.feign.dto.request.RequestCreditDTO;
import com.quanyou.qup.biz.check.feign.dto.response.CheckResponseDTO;
import com.quanyou.qup.core.bean.base.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *  信贷 检查，熔断降级处理
 * @author kevin
 * @date 2020/8/10 13:09
 * @since 1.0.0
 */
@Slf4j
@Component
public class RemoteCreditServiceFallbackImpl implements RemoteCreditService {

    @Override
    public ResponseEntity<CheckResponseDTO> getCreditAndCheck(RequestCreditDTO creditDTO) {
        return ResponseEntity.error("search error！");
    }

    @Override
    public ResponseEntity<Boolean> checkCredit(RequestCreditDTO creditDTO) {
        return ResponseEntity.error("search error！");
    }

}
