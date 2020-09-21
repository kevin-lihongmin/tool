package com.quanyou.qup.biz.check.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.quanyou.qup.biz.check.constant.CheckTypeEnum;
import com.quanyou.qup.biz.check.dto.request.CheckChainDTO;
import com.quanyou.qup.biz.check.dto.request.CheckRequestDTO;
import com.quanyou.qup.core.util.JSONUtil;
import com.quanyou.qup.middle.common.enums.BooleanEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.quanyou.qup.biz.check.constant.BusinessExceptionEnum.PARAMETER_MATCH_EXCEPTION;
import static com.quanyou.qup.biz.check.constant.BusinessExceptionEnum.buildException;

/**
 *  检查链调用 入参按照枚举类型进行反序列化，对应的{@link CheckChainDTO# checkRequestDTO} 类型
 *
 * @author kevin
 * @date 2020/9/11 16:20
 * @since 1.0.0
 */
@Slf4j
public class CheckChainDTOJsonDeserializer extends JsonDeserializer<CheckChainDTO> {

    @Override
    public CheckChainDTO deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        BooleanEnum booleanEnum = null;
        try {
            final String checkTypeEnumStr = node.get("checkTypeEnum").asText();
            final CheckTypeEnum checkTypeEnum = CheckTypeEnum.valueOf(checkTypeEnumStr);

            final String getResultStr = node.get("getResult").asText();
            booleanEnum = BooleanEnum.valueOf(getResultStr);

            final String checkRequestDTOStr = node.get("checkRequestDTO").toString();

            final CheckRequestDTO checkRequestDTO = JSONUtil.deserializeObject(checkRequestDTOStr, checkTypeEnum.clazz);
            return new CheckChainDTO(checkTypeEnum, booleanEnum, checkRequestDTO);
        } catch (Exception e) {
            log.error("检查接口调用链路异常：" + e);
            buildException(PARAMETER_MATCH_EXCEPTION, booleanEnum == null ? "" : booleanEnum);
        }

        return null;
    }
}