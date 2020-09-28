package com.common.service;

import com.quanyou.qup.core.collection.CollUtil;
import com.quanyou.qup.core.exception.BusinessException;
import com.quanyou.qup.core.util.ConvertUtil;
import com.quanyou.qup.core.util.FeignUtil;
import com.quanyou.qup.core.util.StringUtil;
import com.quanyou.qup.gcenter.feign.RemoteGoodsService;
import com.quanyou.qup.gcenter.feign.dto.RemoteSimpleCombinationDTO;
import com.quanyou.qup.gcenter.feign.dto.RemoteSimpleGoodsDTO;
import com.quanyou.qup.mdm.feign.RemoteProductBomService;
import com.quanyou.qup.mdm.feign.RemoteProductInfoService;
import com.quanyou.qup.mdm.feign.dto.RemoteBomStructDTO;
import com.quanyou.qup.mdm.feign.dto.RemoteProductInfoMainDTO;
import com.quanyou.qup.middle.common.bean.*;
import com.quanyou.qup.middle.common.enums.BomStructEnum;
import com.quanyou.qup.middle.common.enums.ProductStructEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 产品结构服务
 *
 * @author 抓抓匠
 * @since 2020-08-26
 */
@Slf4j
@Service
@ConditionalOnClass(value = {RemoteGoodsService.class, RemoteProductInfoService.class, RemoteProductBomService.class})
public class ProductStructService {
    private RemoteGoodsService remoteGoodsService;
    private RemoteProductInfoService remoteProductInfoService;
    private RemoteProductBomService remoteProductBomService;

    public ProductStructService(RemoteGoodsService remoteGoodsService, RemoteProductInfoService remoteProductInfoService, RemoteProductBomService remoteProductBomService) {
        this.remoteGoodsService = remoteGoodsService;
        this.remoteProductInfoService = remoteProductInfoService;
        this.remoteProductBomService = remoteProductBomService;
    }

    /**
     * 查询产品结构
     */
    public List<ProductStruct> listProductStruct(ProductStructRequest request) {
        if (!check(request)) {
            return new ArrayList<>(0);
        }
        //组合
        List<RemoteSimpleCombinationDTO> combinations = listRemoteSimpleCombinations(request);
        //商品
        List<RemoteSimpleGoodsDTO> goodsList = listRemoteSimpleGoods(request);
        //BOM结构
        List<RemoteBomStructDTO> bomStructList = listRemoteBomStruct(request, combinations, goodsList);
        //产品
        List<RemoteProductInfoMainDTO> productInfos = listRemoteProductInfoMains(request, combinations, goodsList, bomStructList);
        //查询结构
        QueryResponse response = new QueryResponse(combinations, goodsList, bomStructList, productInfos);

        return assembly(request, response);
    }

    private boolean check(ProductStructRequest request) {
        if (request == null) {
            return false;
        }
        if (CollUtil.isEmpty(request.getCombinationCodes())
                && CollUtil.isEmpty(request.getSkuCodes())
                && CollUtil.isEmpty(request.getProductCodes())) {
            return false;
        }
        return true;
    }

    private List<RemoteSimpleCombinationDTO> listRemoteSimpleCombinations(ProductStructRequest request) {
        if (CollUtil.isEmpty(request.getCombinationCodes())) {
            return new ArrayList<>(0);
        }
        List<RemoteSimpleCombinationDTO> combinations = FeignUtil.getData(remoteGoodsService.listSimpleCombinationByCodes(request.getCombinationCodes()));
        if (combinations == null) {
            combinations = new ArrayList<>(0);
        }
        return combinations;
    }

    private List<RemoteSimpleGoodsDTO> listRemoteSimpleGoods(ProductStructRequest request) {
        if (CollUtil.isEmpty(request.getSkuCodes())) {
            return new ArrayList<>(0);
        }
        List<RemoteSimpleGoodsDTO> goodsList = FeignUtil.getData(remoteGoodsService.listSimpleBySkuCodes(request.getSkuCodes()));
        if (goodsList == null) {
            goodsList = new ArrayList<>(0);
        }
        return goodsList;
    }

    private List<RemoteBomStructDTO> listRemoteBomStruct(ProductStructRequest request,
                                                         List<RemoteSimpleCombinationDTO> combinations,
                                                         List<RemoteSimpleGoodsDTO> goodsList) {
        Set<String> productCodes = new HashSet<>();
        //添加组合商品中所有的产品
        combinations.forEach(
                combination -> combination.getGoodsList().forEach(goods -> productCodes.add(goods.getProductCode()))
        );
        //添加商品商品中的产品编码
        goodsList.forEach(goods -> productCodes.add(goods.getProductCode()));
        //添加查询条件中的产品编码
        if (CollUtil.isNotEmpty(request.getProductCodes())) {
            productCodes.addAll(request.getProductCodes());
        }

        List<RemoteBomStructDTO> bomStructList = FeignUtil.getData(remoteProductBomService.listBomStruct(new ArrayList<>(productCodes)));
        if (bomStructList == null) {
            bomStructList = new ArrayList<>(0);
        }
        return bomStructList;
    }

    private List<RemoteProductInfoMainDTO> listRemoteProductInfoMains(ProductStructRequest request,
                                                                      List<RemoteSimpleCombinationDTO> combinations,
                                                                      List<RemoteSimpleGoodsDTO> goodsList,
                                                                      List<RemoteBomStructDTO> bomStructList) {
        Set<String> productCodes = new HashSet<>();
        //添加组合商品中所有的产品
        combinations.forEach(
                combination -> combination.getGoodsList().forEach(goods -> productCodes.add(goods.getProductCode()))
        );
        //添加商品商品中的产品编码
        goodsList.forEach(goods -> {
            if (StringUtil.isEmpty(goods.getProductCode())) {
                throw new BusinessException("SKU["+goods.getSkuCode()+"]没有产品编码");
            }
            productCodes.add(goods.getProductCode());
        });
        //添加BOM结构中的产品
        bomStructList.forEach(
                bom -> {
                    productCodes.add(bom.getProductCode());
                    if (CollUtil.isNotEmpty(bom.getChildren())) {
                        bom.getChildren().forEach(childBom -> productCodes.add(childBom.getProductCode()));
                    }
                }
        );
        //添加查询条件中的产品编码
        if (CollUtil.isNotEmpty(request.getProductCodes())) {
            productCodes.addAll(request.getProductCodes());
        }

        List<RemoteProductInfoMainDTO> productInfos = FeignUtil.getData(remoteProductInfoService.listMainProductInfoByProductCodes(new ArrayList<>(productCodes)));
        if (productInfos == null) {
            productInfos = new ArrayList<>(0);
        }
        return productInfos;
    }

    private List<ProductStruct> assembly(ProductStructRequest request, QueryResponse response) {
        List<ProductStruct> productStructList = new ArrayList<>();
        //组合商品
        productStructList.addAll(assemblyByCombination(request.getCombinationCodes(), response));
        productStructList.addAll(assemblyBySku(request.getSkuCodes(), response));
        productStructList.addAll(assemblyByProduct(request.getProductCodes(), response));

        return productStructList;
    }

    private List<ProductStruct> assemblyByCombination(List<String> combinationCodes, QueryResponse response) {
        if (CollUtil.isEmpty(combinationCodes)) {
            return new ArrayList<>(0);
        }
        List<ProductStruct> productStructList = new ArrayList<>();
        for (String combinationCode : combinationCodes) {
            RemoteSimpleCombinationDTO combinationDTO = response.findCombination(combinationCode);
            if (combinationDTO == null) {
                continue;
            }
            List<ProductStruct> children = new ArrayList<>();
            for (RemoteSimpleGoodsDTO goods : combinationDTO.getGoodsList()) {
                ProductStruct child = assemblyBySku(goods, goods.getSkuNum(), response);
                if (child != null) {
                    children.add(child);
                }
            }
            Combination combination = ConvertUtil.convertObject(Combination.class, combinationDTO);
            productStructList.add(new ProductStruct(combination, children));
        }
        return productStructList;
    }

    private List<ProductStruct> assemblyBySku(List<String> skuCodes, QueryResponse response) {
        if (CollUtil.isEmpty(skuCodes)) {
            return new ArrayList<>(0);
        }
        List<ProductStruct> productStructList = new ArrayList<>();
        for (String skuCode : skuCodes) {
            ProductStruct productStruct = assemblyBySku(skuCode, response);
            if (productStruct != null) {
                productStructList.add(productStruct);
            }
        }
        return productStructList;
    }

    private ProductStruct assemblyBySku(String skuCode, QueryResponse response) {
        return assemblyBySku(response.findGoods(skuCode), 1, response);
    }

    private ProductStruct assemblyBySku(RemoteSimpleGoodsDTO simpleGoods, int num, QueryResponse response) {
        if (simpleGoods == null) {
            return null;
        }
        List<ProductStruct> children = new ArrayList<>(1);
        children.add(assemblyByProduct(simpleGoods.getProductCode(), 1, response));
        Goods goods = ConvertUtil.convertObject(Goods.class, simpleGoods);

        return new ProductStruct(goods, num, children);
    }

    private List<ProductStruct> assemblyByProduct(List<String> productCodes, QueryResponse response) {
        if (CollUtil.isEmpty(productCodes)) {
            return new ArrayList<>(0);
        }
        List<ProductStruct> productStructList = new ArrayList<>();
        for (String productCode : productCodes) {
            ProductStruct productStruct = assemblyByProduct(productCode, 1, response);
            if (productStruct != null) {
                productStructList.add(productStruct);
            }
        }
        return productStructList;
    }

    private ProductStruct assemblyByProduct(String productCode, int num, QueryResponse response) {
        RemoteProductInfoMainDTO productInfoDTO = response.findProductInfo(productCode);
        if (productInfoDTO == null) {
            return null;
        }
        ProductInfo productInfo = ConvertUtil.convertObject(ProductInfo.class, productInfoDTO);
        //BOM结构
        RemoteBomStructDTO bomStruct = response.findBomStruct(productCode);
        if (bomStruct == null || CollUtil.isEmpty(bomStruct.getChildren())) {
            return new ProductStruct(productInfo, ProductStructEnum.PACK_STRUCT, num, new ArrayList<>());
        }
        List<ProductStruct> children = new ArrayList<>();
        for (RemoteBomStructDTO struct : bomStruct.getChildren()) {
            ProductStruct child = assemblyByProduct(struct.getProductCode(), struct.getNum(), response);
            if (child != null) {
                children.add(child);
            }
        }
        if (bomStruct.getType().equals(BomStructEnum.saleBom.getCode())) {
            return new ProductStruct(productInfo, ProductStructEnum.SALE_STRUCT, num, children);
        } else {
            return new ProductStruct(productInfo, ProductStructEnum.SUITE_STRUCT, num, children);
        }
    }

    @Data
    private class QueryResponse {
        /**
         * 组合SKU
         */
        List<RemoteSimpleCombinationDTO> combinations;

        /**
         * SKU
         */
        List<RemoteSimpleGoodsDTO> goodsList;

        /**
         * BOM结构
         */
        List<RemoteBomStructDTO> bomStructList;

        /**
         * 产品
         */
        List<RemoteProductInfoMainDTO> productInfos;


        private QueryResponse(List<RemoteSimpleCombinationDTO> combinations,
                              List<RemoteSimpleGoodsDTO> goodsList,
                              List<RemoteBomStructDTO> bomStructList,
                              List<RemoteProductInfoMainDTO> productInfos) {
            this.combinations = combinations;
            this.goodsList = goodsList;
            this.bomStructList = bomStructList;
            this.productInfos = productInfos;
        }

        private RemoteSimpleCombinationDTO findCombination(String combinationCode) {
            if (CollUtil.isEmpty(combinations)) {
                return null;
            }
            for (RemoteSimpleCombinationDTO combination : combinations) {
                if (combination.getCode().equals(combinationCode)) {
                    return combination;
                }
            }
            return null;
        }

        private RemoteSimpleGoodsDTO findGoods(String skuCode) {
            if (CollUtil.isEmpty(goodsList)) {
                return null;
            }
            for (RemoteSimpleGoodsDTO goods : goodsList) {
                if (goods.getSkuCode().equals(skuCode)) {
                    return goods;
                }
            }
            return null;
        }

        private RemoteProductInfoMainDTO findProductInfo(String productCode) {
            if (CollUtil.isEmpty(productInfos)) {
                return null;
            }
            for (RemoteProductInfoMainDTO productInfo : productInfos) {
                if (productInfo.getProductCode().equals(productCode)) {
                    return productInfo;
                }
            }
            return null;
        }

        private RemoteBomStructDTO findBomStruct(String productCode) {
            if (CollUtil.isEmpty(bomStructList)) {
                return null;
            }
            for (RemoteBomStructDTO struct : bomStructList) {
                if (struct.getProductCode().equals(productCode)) {
                    return struct;
                }
            }
            return null;
        }
    }
}
