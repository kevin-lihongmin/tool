package com.quanyou.qup.biz.check.feign;

import com.quanyou.qup.biz.check.feign.dto.enums.SelectByEnum;
import com.quanyou.qup.biz.check.feign.dto.response.CheckResponseDTO;
import com.quanyou.qup.biz.check.feign.fallback.RemoteAccountServiceFallbackImpl;
import com.quanyou.qup.core.bean.base.ResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *  业务账号 服务 Feign
 * @author kevin
 * @date 2020/8/10 13:14
 * @since 1.0.0
 */
@FeignClient(value = "biz-check-service",contextId = "remoteBizCheckAccountService", fallback = RemoteAccountServiceFallbackImpl.class)
public interface RemoteAccountService {

    /**
     *  获取业务账号明细信息
     * @param idOrCode 主键
     * @return 业务账号明细信息
     */
    @GetMapping("/provider/v1/account/getAccountAndCheck")
    ResponseEntity<CheckResponseDTO> getAccountAndCheck(@RequestParam("idOrCode") String idOrCode,
                                                        @RequestParam("selectByEnum") SelectByEnum selectByEnum);

    /**
     *  验证业务账号信息
     * @param idOrCode 主键或编码
     * @param selectByEnum 查询类型枚举
     * @return 业务账号状态是否正常
     */
    @GetMapping("/provider/v1/account/checkAccount")
    ResponseEntity<Boolean> checkAccount(@RequestParam("idOrCode") String idOrCode,
                                         @RequestParam("selectByEnum") SelectByEnum selectByEnum);

}