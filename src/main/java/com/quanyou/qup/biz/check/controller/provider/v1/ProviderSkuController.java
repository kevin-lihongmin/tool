package com.quanyou.qup.biz.check.controller.provider.v1;

import com.quanyou.qup.biz.check.constant.SelectByEnum;
import com.quanyou.qup.biz.check.dto.response.CheckResponseDTO;
import com.quanyou.qup.biz.check.service.AccountService;
import com.quanyou.qup.biz.check.service.SkuService;
import com.quanyou.qup.core.bean.base.ResponseEntity;
import com.quanyou.qup.middle.common.dto.SkuInfoDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.quanyou.qup.biz.check.controller.provider.v1.ValidCheckDelegate.validId;
import static com.quanyou.qup.biz.check.controller.provider.v1.ValidCheckDelegate.validSkuRequest;

/**
 *  SKU 相关检查
 *
 * @author kevin
 * @date 2020/8/11 13:21
 * @since 1.0.0
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/provider/v1/sku")
public class ProviderSkuController {

    private final SkuService skuService;

    /**
     *  商品（sku、sku组合、产品或单包件）相关检查服务，并获取检查的验证数据
     * @param skuCodeList 商品（sku、sku组合、产品或单包件）集合
     * @return Sku明细信息
     */
    @PostMapping("getSkuAndCheck")
    public ResponseEntity<CheckResponseDTO> getSkuAndCheck(@RequestBody List<SkuInfoDTO> skuCodeList) {
        validSkuRequest(skuCodeList);
        CheckResponseDTO customer = skuService.adaptCheck(skuCodeList);
        return ResponseEntity.success(customer);
    }

    /**
     *  商品（sku、sku组合、产品或单包件）相关检查服务
     * @param skuCodeList 商品（sku、sku组合、产品或单包件）集合
     * @return Sku状态是否正常
     */
    @PostMapping("checkSku")
    public ResponseEntity<Boolean> checkSku(@RequestBody List<SkuInfoDTO> skuCodeList) {
        validSkuRequest(skuCodeList);
        Boolean isNatural = skuService.adaptCheckPass(skuCodeList);
        return ResponseEntity.success(isNatural);
    }

}