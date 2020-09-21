package com.quanyou.qup.biz.check.manager.sku;

import com.quanyou.qup.biz.check.client.SkuFeignService;
import com.quanyou.qup.core.collection.CollectionUtil;
import com.quanyou.qup.middle.common.dto.SkuInfoDTO;
import com.quanyou.qup.middle.common.enums.SkuTypeEnum;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.quanyou.qup.middle.common.enums.SkuTypeEnum.SKU;


/**
 *  sku状态检查，
 *  1、获取sku信息和对应的产品编码 ；并检查sku状态
 *  2、根据产品编码获取产品、套件、包件等信息，并进行检查
 *
 * @author kevin
 * @date 2020/8/26 9:48
 * @since 1.0.0
 */
public class SkuCheckSkuInfo implements CheckSkuInfo {

    @Resource
    protected SkuFeignService skuFeignService;

    /**
     * 产品是否启用
     */
    private static final String ENABLE = "Y";

    /**
     * 下一个检查项配置的为sku组合
     */
    private CheckSkuInfo next;

    @Override
    public void checkChain(Map<SkuTypeEnum, List<SkuInfoDTO>> groupSku) {
        List<String> collect = groupSku.get(SKU).stream().map(sku -> sku.code).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(collect)) {
            next.checkChain(groupSku);
        }

        /*List<Remote2BGoodsDTO> remote2BGoodsDTOS = skuFeignService.listSku(collect.toArray(new String[0]));
        List<String> errorList = remote2BGoodsDTOS.stream().filter(sku -> ENABLE.equals(sku.getSku().getEnableFlag()))
                .map(sku -> sku.getSku().getSkuName()).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(errorList)) {
            // 组合错误信息

        }

        List<String> productCodeList = remote2BGoodsDTOS.stream().map(product -> product.getSku().getMdmCode()).collect(Collectors.toList());*/


        next.checkChain(groupSku);
    }

    public SkuCheckSkuInfo(CheckSkuInfo next) {
        this.next = next;
    }
}
