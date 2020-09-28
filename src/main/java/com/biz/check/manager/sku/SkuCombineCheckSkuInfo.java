package com.biz.check.manager.sku;

import com.quanyou.qup.middle.common.dto.SkuInfoDTO;
import com.quanyou.qup.middle.common.enums.SkuTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;

import static com.quanyou.qup.middle.common.enums.SkuTypeEnum.COMBINE;


/**
 *  sku状态检查，
 *  1、根据商品组合编码获取 sku信息和对应的产品编码 ；并检查sku状态
 *  2、根据产品编码获取产品、套件、包件等信息，并进行检查
 *
 * @author kevin
 * @date 2020/8/26 9:48
 * @since 1.0.0
 */
@Slf4j
public class SkuCombineCheckSkuInfo extends SkuCheckSkuInfo implements CheckSkuInfo, BeanNameAware {

    /**
     * 是否启用状态
     */
    private static final String ENABLE = "Y";

    /**
     *  两层都会调用该方法打印，查看是否正确初始化
     * @param name bean名称
     */
    @Override
    public void setBeanName(@NonNull String name) {
        log.info("Bean: " + this.getClass().getName() + ", bean name is " + name);
    }

    public SkuCombineCheckSkuInfo() {
        super(null);
    }

    @Override
    public void checkChain(Map<SkuTypeEnum, List<SkuInfoDTO>> groupSku) {
        List<SkuInfoDTO> skuInfoDTOS = groupSku.get(COMBINE);
        if (skuInfoDTOS == null) {
            return;
        }
        String[] combineCodes = skuInfoDTOS.stream().map(sku -> sku.code).toArray(String[]::new);
    /*    List<Remote2BCombinationDTO> skuCombineInfo = skuFeignService.listSkuCombine(combineCodes);

        List<String> error = new ArrayList<>();

        skuCombineInfo.forEach(combineSku -> {
            if (!ENABLE.equals(combineSku.getEnableFlag())) {
                error.add("组合商品" + combineSku.getCode() + "状态为异常！");
            }
            List<String> errorList = combineSku.getGoodsList().stream().filter(sku -> ENABLE.equals(sku.getSku().getEnableFlag()))
                    .map(sku -> sku.getSku().getSkuName()).collect(Collectors.toList());
            error.addAll(errorList);
        });

        List<List<String>> collect = skuCombineInfo.stream().map(skuCombine ->
                skuCombine.getGoodsList().stream().map(sku -> sku.getSku().getMdmCode()).collect(Collectors.toList())
            ).collect(Collectors.toList())*//*.map()*//*;
*/

        // 组装返回结果
    }
}
