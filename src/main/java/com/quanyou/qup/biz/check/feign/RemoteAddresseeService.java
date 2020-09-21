package com.quanyou.qup.biz.check.feign;

import com.quanyou.qup.biz.check.feign.dto.enums.SelectByEnum;
import com.quanyou.qup.biz.check.feign.dto.response.CheckResponseDTO;
import com.quanyou.qup.biz.check.feign.fallback.RemoteAddresseeServiceFallbackImpl;
import com.quanyou.qup.core.bean.base.ResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *  送达方 服务 Feign
 * @author kevin
 * @date 2020/8/10 13:14
 * @since 1.0.0
 */
@FeignClient(value = "biz-check-service", contextId = "remoteBizCheckAddresseeService", fallback = RemoteAddresseeServiceFallbackImpl.class)
public interface RemoteAddresseeService {

    /**
     *  获取送达方明细信息
     * @param idOrCode 主键
     * @return 送达方明细信息
     */
    @GetMapping("/provider/v1/addressee/getAddresseeAndCheck")
    ResponseEntity<CheckResponseDTO> getAddresseeAndCheck(@RequestParam("idOrCode") String idOrCode,
                                                          @RequestParam("selectByEnum") SelectByEnum selectByEnum);

    /**
     *  验证送达方信息
     * @param idOrCode 主键或编码
     * @param selectByEnum 查询类型枚举
     * @return 送达方状态是否正常
     */
    @GetMapping("/provider/v1/addressee/checkAddressee")
    ResponseEntity<Boolean> checkAddressee(@RequestParam("idOrCode") String idOrCode,
                                           @RequestParam("selectByEnum") SelectByEnum selectByEnum);

}