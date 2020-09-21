package com.quanyou.qup.biz.check.feign.fallback;

import com.quanyou.qup.biz.check.feign.RemoteAccountService;
import com.quanyou.qup.biz.check.feign.dto.enums.SelectByEnum;
import com.quanyou.qup.biz.check.feign.dto.response.CheckResponseDTO;
import com.quanyou.qup.core.bean.base.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *  业务账号检查，熔断降级处理
 * @author kevin
 * @date 2020/8/10 13:09
 * @since 1.0.0
 */
@Slf4j
@Component
public class RemoteAccountServiceFallbackImpl implements RemoteAccountService {

    @Override
    public ResponseEntity<CheckResponseDTO> getAccountAndCheck(String idOrCode, SelectByEnum selectByEnum) {
        return ResponseEntity.error("search error！");
    }

    @Override
    public ResponseEntity<Boolean> checkAccount(String idOrCode, SelectByEnum selectByEnum) {
        return ResponseEntity.error("search error！");
    }
}
