package com.quanyou.qup.biz.check.client;

import com.quanyou.qup.gcenter.feign.RemoteGoodsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 *  商品 Feign服务
 * @author kevin
 * @date 2020/8/14 8:32
 * @since 1.0.0
 */
@Repository
@AllArgsConstructor
public class SkuFeignService {

    private final RemoteGoodsService remoteGoodsService;

    /**
     * 获取商品信息列表
     * @param skuCode sku编码 集合
     * @return sku信息
     */
    /*public List<RemoteSimpleCombinationDTO> listSku(String... skuCode) {
        ResponseEntity<List<RemoteSimpleCombinationDTO>> response = remoteGoodsService.listSimpleBySkuCodes(null);
        if (!response.isSuccess() || CollectionUtil.isEmpty(response.getData())) {
            return new ArrayList<>();
        }
        return response.getData();
    }

    *//**
     * 获取商品组合对应的商品
     * @param skuCombineCode 商品组合编码 集合
     * @return 商品组合信息
     *//*
    public List<RemoteSimpleCombinationDTO> listSkuCombine(String... skuCombineCode) {
        ResponseEntity<List<RemoteSimpleCombinationDTO>> response = remoteGoodsService.listSimpleCombinationByCodes(skuCombineCode);
        if (!response.isSuccess() || CollectionUtil.isEmpty(response.getData())) {
            return new ArrayList<>();
        }
        return response.getData();
    }*/

}
