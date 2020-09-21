package com.quanyou.qup.biz.check.feign;

import com.quanyou.qup.biz.check.feign.dto.enums.SelectByEnum;
import com.quanyou.qup.biz.check.feign.dto.response.CheckResponseDTO;
import com.quanyou.qup.biz.check.feign.fallback.RemoteCustomerServiceFallbackImpl;
import com.quanyou.qup.core.bean.base.ResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *  经销商 服务 Feign
 * @author kevin
 * @date 2020/8/10 13:14
 * @since 1.0.0
 */
@FeignClient(value = "biz-check-service",contextId = "remoteBizCheckCustomerService", fallback = RemoteCustomerServiceFallbackImpl.class)
public interface RemoteCustomerService {

    /**
     *  获取经销商明细信息
     * @param idOrCode 主键
     * @return 经销商明细信息
     */
    @GetMapping("/provider/v1/customer/getCustomerAndCheck")
    ResponseEntity<CheckResponseDTO> getCustomerAndCheck(@RequestParam("idOrCode") String idOrCode,
                                                         @RequestParam("selectByEnum") SelectByEnum selectByEnum);

    /**
     *  验证经销商信息
     * @param idOrCode 主键或编码
     * @param selectByEnum 查询类型枚举
     * @return 经销商状态是否正常
     */
    @GetMapping("/provider/v1/customer/checkCustomer")
    ResponseEntity<Boolean> checkCustomer(@RequestParam("idOrCode") String idOrCode,
                                          @RequestParam("selectByEnum") SelectByEnum selectByEnum);

}