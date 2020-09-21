package com.quanyou.qup.biz.check.controller.provider.v1;

import com.alibaba.fastjson.JSON;
import com.quanyou.qup.biz.check.dto.request.CheckChainDTO;
import com.quanyou.qup.biz.check.dto.request.CheckRequestDTO;
import com.quanyou.qup.core.collection.CollectionUtil;
import com.quanyou.qup.core.exception.BusinessException;
import com.quanyou.qup.core.util.StringUtil;
import com.quanyou.qup.middle.common.dto.SkuInfoDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.quanyou.qup.biz.check.constant.BusinessExceptionEnum.*;

/**
 *  验证委派
 *
 * @author kevin
 * @date 2020/8/12 18:05
 * @since 1.0.0
 */
@Slf4j
public class ValidCheckDelegate {

    /**
     *  {@code id}验证非空
     * @param id 主键
     */
    public static void validId(String id) {
        if (StringUtil.isBlank(id)) {
            buildException(PARAMETER_EXCEPTION);
        }
    }

    /**
     *  检查请求参数验证
     * @param checkChains 检查请求列表
     * @see com.quanyou.qup.biz.check.config.CheckChainDTOJsonDeserializer 已经在反序列化阶段验证了， {@link CheckRequestDTO} 类型
     */
    public static void valid(CheckChainDTO[] checkChains) throws BusinessException {
        log.info("checkChains info = {}", JSON.toJSONString(checkChains));
        if (checkChains == null) {
            buildException(PARAMETER_EXCEPTION);
        }
        for (int i = 0; i < checkChains.length; i++) {
            final CheckChainDTO checkChain = checkChains[i];
            if (checkChain == null) {
                buildException(PARAMETER_EXCEPTION);
            }
            /*if (checkChain.getCheckRequestDTO() instanceof CheckRequestCreditDTO) {

            }
            if (!checkChain.getCheckTypeEnum().validCheckRequestType(checkChain.getCheckRequestDTO())) {
                log.error(String.format(PARAMETER_MATCH_EXCEPTION.message, i + 1, checkChain.getCheckTypeEnum()));
                buildException(PARAMETER_MATCH_EXCEPTION, i + 1, checkChain.getCheckTypeEnum());
            }*/
        }
    }

    /**
     * 商品状态检查，请求验证
     * @param skuCodeList 商品（sku、sku组合、产品或单包件）集合
     */
    public static void validSkuRequest(List<SkuInfoDTO> skuCodeList) {
        if (CollectionUtil.isEmpty(skuCodeList)) {
            buildException(PARAMETER_EMPTY_EXCEPTION);
        }
        skuCodeList.forEach(sku -> {
            if (StringUtil.isBlank(sku.code) || sku.typeEnum == null) {
                buildException(PARAMETER_EMPTY_EXCEPTION);
            }
        });
    }

}
