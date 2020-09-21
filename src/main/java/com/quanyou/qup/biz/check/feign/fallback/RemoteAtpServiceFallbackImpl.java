package com.quanyou.qup.biz.check.feign.fallback;

import com.quanyou.qup.biz.check.feign.RemoteAtpService;
import com.quanyou.qup.biz.check.feign.dto.request.RequestAtpDTO;
import com.quanyou.qup.biz.check.feign.dto.response.AtpInfoResultDTO;
import com.quanyou.qup.core.bean.base.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *  atp检查，熔断降级处理
 * @author kevin
 * @date 2020/8/10 13:09
 * @since 1.0.0
 */
@Slf4j
@Component
public class RemoteAtpServiceFallbackImpl implements RemoteAtpService {

    @Override
    public ResponseEntity<List<AtpInfoResultDTO>> getAtpAndCheck(RequestAtpDTO atpDTO) {
        return ResponseEntity.error("降级");
    }

    @Override
    public ResponseEntity<Boolean> checkAtp(RequestAtpDTO atpDTO) {
        return ResponseEntity.success(Boolean.FALSE);
    }
}
