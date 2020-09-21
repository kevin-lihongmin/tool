package com.quanyou.qup.biz.check.feign.fallback;

import com.quanyou.qup.biz.check.feign.RemoteCheckService;
import com.quanyou.qup.biz.check.feign.dto.request.CheckChainDTO;
import com.quanyou.qup.biz.check.feign.dto.response.CheckResultDTO;
import com.quanyou.qup.core.bean.base.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *  检查服务，熔断降级处理
 * @author kevin
 * @date 2020/8/10 13:09
 * @since 1.0.0
 */
@Slf4j
@Component
public class RemoteCheckServiceFallbackImpl implements RemoteCheckService {

    @Override
    public ResponseEntity<CheckResultDTO> checkChain(CheckChainDTO... checkChains) {
        log.error("检查链，走降级服务！");
        return ResponseEntity.error("search error！");
    }
}
