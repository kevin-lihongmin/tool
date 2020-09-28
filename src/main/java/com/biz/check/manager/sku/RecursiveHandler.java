package com.biz.check.manager.sku;

import com.google.common.collect.Lists;
import com.biz.check.dto.response.SkuResultDTO;
import com.quanyou.qup.core.collection.CollectionUtil;
import com.quanyou.qup.middle.common.bean.*;
import com.quanyou.qup.middle.common.enums.ProductStateEnum;
import com.quanyou.qup.middle.common.enums.ProductStructEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.biz.check.dto.response.SkuResultDTO.ErrorTrace;

/**
 *  根据类型，递归处理产品结构
 * @author kevin
 * @date 2020/8/28 15:20
 * @since 1.0.0
 */
public class RecursiveHandler {

    /**
     *  层调用策略
     * @author kevin
     * @date 2020/8/28 15:56
     * @since 1.0.0
     */
    @SuppressWarnings("unused")
    public enum Strategy {

        /** 组合与SKU结构 */
        COMBINATION_STRUCT {
            @Override
            public void recursiveHandle(ProductStruct struct, SkuResultDTO result, String code, List<ProductStruct> structLevel) {
                Combination combination = struct.getCombination();
                /*if ("UNABLE".equals(combination.getPurchaseHandle()) || !ProductStateEnum.isNormal(combination.getProductState())) {
                    wrapResult(result, code, structLevel);
                }*/
            }
        },
        /** SKU与产品结构 */
        SKU_STRUCT {
            @Override
            public void recursiveHandle(ProductStruct struct, SkuResultDTO result, String code, List<ProductStruct> structLevel) {
                Goods goods = struct.getGoods();
                if (goods == null) {
                    return;
                }
                /*Sku sku = goods.getSku();
                if (!ProductStateEnum.isNormal(sku.getProductState()) || "N".equals(sku.getEnableFlag())) {
                    wrapResult(result, code, structLevel);
                }*/
            }
        },
        /** 销售产品与产品（套件或包件）结构 */
        SALE_STRUCT {
            @Override
            public void recursiveHandle(ProductStruct struct, SkuResultDTO result, String code, List<ProductStruct> structLevel) {
                handlerProductInfo(struct, result, code, structLevel);
            }
        },
        /** 套件与包件结构 */
        SUITE_STRUCT {
            @Override
            public void recursiveHandle(ProductStruct struct, SkuResultDTO result, String code, List<ProductStruct> structLevel) {
                handlerProductInfo(struct, result, code, structLevel);
            }
        },
        /** 包件 */
        PACK_STRUCT {
            @Override
            public void recursiveHandle(ProductStruct struct, SkuResultDTO result, String code, List<ProductStruct> structLevel) {
                handlerProductInfo(struct, result, code, structLevel);
            }
        };
        /**
         *  操作产品结构
         */
        private static void handlerProductInfo(ProductStruct struct, SkuResultDTO result, String code, List<ProductStruct> structLevel) {
            ProductInfo productInfo = struct.getProductInfo();
            if (productInfo == null) {
                return;
            }
            if (!ProductStateEnum.isNormal(productInfo.getProState())) {
                wrapResult(result, code, structLevel);
            }
        }

        /**
         * 组装错误信息
         * @param result 总结果
         * @param code 顶层错误编码
         * @param structLevel 当前层级结构
         */
        private static void wrapResult(SkuResultDTO result, String code, List<ProductStruct> structLevel) {
            List<ErrorTrace> collect = structLevel.stream().map(current -> new ErrorTrace(current.getType(), current.getCode())).collect(Collectors.toList());
            result.errorTraceList.add(collect);
            result.errorCodeSet.add(code);
        }

        /**
         * 底层实现的不同逻辑
         * @param struct 产品结构
         * @param result 结果容器
         * @param code 顶层（sku、sku组合、单包件）编码
         * @param structLevel 结构层级
         */
        public abstract void recursiveHandle(ProductStruct struct, SkuResultDTO result, String code, List<ProductStruct> structLevel);

        /**
         * 根据类型初始化参数项
         * @param struct 当前层级的产品统一结构
         * @param code 顶层（sku、sku组合、单包件）编码
         * @param structLevel 结构层级
         */
        public void process(ProductStruct struct, SkuResultDTO result, String code, List<ProductStruct> structLevel) {
            if (struct == null) {
                return;
            }
            // 添加，只是将当前的指针指向该容器
            structLevel.add(struct);
            // 调用不同层的实现逻辑
            recursiveHandle(struct, result, code, structLevel);

            // 调用下一层
            if (CollectionUtil.isNotEmpty(struct.getChildren())) {
                struct.getChildren().forEach(child -> Strategy.valueOf(child.getType().name()).recursiveHandle(child, result, code, structLevel));
            }
        }
    }

    /**
     * 根据类型，组装初始化的检查入参
     */
    public static void recursiveHandle(ProductStruct struct, SkuResultDTO result, ProductStructEnum structEnum) {
        ArrayList<ProductStruct> structLevel = Lists.newArrayList(struct);
        Strategy.valueOf(structEnum.name()).process(struct, result, struct.getCode(), structLevel);
    }

}
