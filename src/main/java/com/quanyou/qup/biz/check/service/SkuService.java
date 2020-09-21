package com.quanyou.qup.biz.check.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.quanyou.qup.biz.check.dto.request.CheckRequestDTO;
import com.quanyou.qup.biz.check.dto.request.CheckRequestSkuDTO;
import com.quanyou.qup.biz.check.dto.response.CheckResponseDTO;
import com.quanyou.qup.biz.check.dto.response.SkuResultDTO;
import com.quanyou.qup.biz.check.manager.sku.RecursiveHandler;
import com.quanyou.qup.biz.check.manager.sku.SkuChainContext;
import com.quanyou.qup.core.collection.CollectionUtil;
import com.quanyou.qup.middle.common.bean.ProductStruct;
import com.quanyou.qup.middle.common.bean.ProductStructRequest;
import com.quanyou.qup.middle.common.dto.SkuInfoDTO;
import com.quanyou.qup.middle.common.enums.SkuTypeEnum;
import com.quanyou.qup.middle.common.service.ProductStructService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  商品检测服务， 检测商品， 以及商品下面的产品，包件是否可用
 * @author kevin
 * @date 2020/8/14 8:31
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class SkuService extends SkuChainContext {

    /**
     * sku和产品统一结构查询服务
     */
    private final ProductStructService productStructService;

    @Override
    protected CheckResponseDTO callService(CheckRequestDTO checkRequestDTO) {
        SkuResultDTO skuResultDTO = invokeCheckSku(checkRequestDTO);
        return new CheckResponseDTO(CollectionUtil.isEmpty(skuResultDTO.errorCodeSet), skuResultDTO);
    }

    @Override
    protected Boolean checkPass(CheckRequestDTO checkRequestDTO) {
        SkuResultDTO skuResultDTO = invokeCheckSku(checkRequestDTO);
        return CollectionUtil.isEmpty(skuResultDTO.errorCodeSet);
    }

    /**
     * 适配查询sku、sku组合、单包件 所有状态是否可用， 并且返回所有信息
     * @param skuCodeList 查询请求
     * @return 最新状态是否都正常
     */
    public CheckResponseDTO adaptCheck(List<SkuInfoDTO> skuCodeList) {
        return callService(new CheckRequestSkuDTO(skuCodeList));
    }

    /**
     * 适配查询sku、sku组合、单包件 所有状态是否可用
     * @param skuCodeList 查询请求
     * @return 是否验证通过
     */
    public Boolean adaptCheckPass(List<SkuInfoDTO> skuCodeList) {
        return checkPass(new CheckRequestSkuDTO(skuCodeList));
    }

    /**
     * 执行检查sku信息
     * @param checkRequestDTO 检查请求
     * @return 错误消息
     */
    private SkuResultDTO invokeCheckSku(CheckRequestDTO checkRequestDTO) {
        CheckRequestSkuDTO skuDTO = (CheckRequestSkuDTO)checkRequestDTO;
        // 构建查询统一结构请求
        ProductStructRequest request = buildStructRequest(skuDTO);

        List<ProductStruct> productStruct = productStructService.listProductStruct(request);
        // 初始化返回结果
        final SkuResultDTO result = new SkuResultDTO(Lists.newArrayList(), Sets.newHashSet());
        // 执行递归调用每一层检验是否可用
        productStruct.forEach(good -> RecursiveHandler.recursiveHandle(good, result, good.getType()));
        return result;
    }

    /**
     * 构建查询统一结构请求
     * @param skuDTO sku查询请求
     * @return 统一结构查询请求
     */
    private ProductStructRequest buildStructRequest(CheckRequestSkuDTO skuDTO) {
        ProductStructRequest request = new ProductStructRequest();
        Map<SkuTypeEnum, List<SkuInfoDTO>> collect = skuDTO.getSkuCodeList().stream().collect(Collectors.groupingBy(sku -> sku.typeEnum));
        if (CollectionUtil.isNotEmpty(collect.get(SkuTypeEnum.COMBINE))) {
            request.setCombinationCodes(collect.get(SkuTypeEnum.COMBINE).stream().map(info -> info.code).collect(Collectors.toList()));
        }
        if (CollectionUtil.isNotEmpty(collect.get(SkuTypeEnum.SKU))) {
            request.setSkuCodes(collect.get(SkuTypeEnum.SKU).stream().map(info -> info.code).collect(Collectors.toList()));
        }
        if (CollectionUtil.isNotEmpty(collect.get(SkuTypeEnum.SINGLE_PACK))) {
            request.setProductCodes(collect.get(SkuTypeEnum.SINGLE_PACK).stream().map(info -> info.code).collect(Collectors.toList()));
        }
        return request;
    }

}
