package com.quanyou.qup.biz.check.controller.provider.v1;

import com.quanyou.qup.biz.check.dto.request.RequestCreditDTO;
import com.quanyou.qup.biz.check.dto.response.CheckResponseDTO;
import com.quanyou.qup.biz.check.service.CreditService;
import com.quanyou.qup.core.bean.base.ResponseEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static com.quanyou.qup.biz.check.constant.CreditSelectByEnum.CREDIT_SIMPLE;

/**
 *  信贷相关检查
 *
 * @author kevin
 * @date 2020/8/11 13:21
 * @since 1.0.0
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/provider/v1/credit")
public class ProviderCreditController {

    private final CreditService creditService;

    /**
     *  获取信贷明细信息
     * @param creditDTO 信贷请求
     * @return 信贷明细信息
     */
    @PostMapping("getCreditAndCheck")
    public ResponseEntity<CheckResponseDTO> getCreditAndCheck(@RequestBody RequestCreditDTO creditDTO) {
        setDefaultValue(creditDTO);
        CheckResponseDTO customer = creditService.adaptCheck(creditDTO);
        return ResponseEntity.success(customer);
    }

    /**
     *  验证信贷信息
     * @param creditDTO 信贷请求
     * @return 信贷验证是否通过
     */
    @PostMapping("checkCredit")
    public ResponseEntity<Boolean> checkCredit(@RequestBody RequestCreditDTO creditDTO) {
        setDefaultValue(creditDTO);
        Boolean isNatural = creditService.adaptCheckPass(creditDTO);
        return ResponseEntity.success(isNatural);
    }

    /**
     * 为信贷查询设置默认值
     * @param creditDTO 信贷查询请求
     */
    private void setDefaultValue(RequestCreditDTO creditDTO) {
        if (creditDTO.getCostAmount() == null) {
            creditDTO.setCostAmount(BigDecimal.ZERO);
        }
        if (creditDTO.getCreditSelectByEnum() == null) {
            creditDTO.setCreditSelectByEnum(CREDIT_SIMPLE);
        }
    }

}