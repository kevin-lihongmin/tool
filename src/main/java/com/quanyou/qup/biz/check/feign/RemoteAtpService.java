package com.quanyou.qup.biz.check.feign;

import com.quanyou.qup.biz.check.feign.dto.request.RequestAtpDTO;
import com.quanyou.qup.biz.check.feign.dto.response.AtpInfoResultDTO;
import com.quanyou.qup.biz.check.feign.fallback.RemoteAtpServiceFallbackImpl;
import com.quanyou.qup.core.bean.base.ResponseEntity;
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
@FeignClient(value = "biz-check-service", contextId = "remoteAtpService",
        fallback = RemoteAtpServiceFallbackImpl.class, path = "/provider/v1/atp")
public interface RemoteAtpService {

    /**
     *  获取可用量明细信息
     * @param atpDTO 可用量请求
     * @return 可用量明细信息
     */
    @PostMapping("getAtpAndCheck")
    ResponseEntity<List<AtpInfoResultDTO>> getAtpAndCheck(@RequestBody RequestAtpDTO atpDTO);

    /**
     *  验证可用量信息
     * @param atpDTO 可用量请求
     * @return 可用量验证是否通过
     */
    @PostMapping("checkAtp")
    ResponseEntity<Boolean> checkAtp(@RequestBody RequestAtpDTO atpDTO);

}