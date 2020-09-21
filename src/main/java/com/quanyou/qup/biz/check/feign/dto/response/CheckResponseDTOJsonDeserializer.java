package com.quanyou.qup.biz.check.feign.dto.response;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.quanyou.qup.biz.check.feign.dto.enums.CheckTypeEnum;
import com.quanyou.qup.core.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 *  检查链调用 返回值按照枚举类型进行反序列化，对应的{@link CheckResponseDTO# resultDTO} 类型
 * @author kevin
 * @date 2020/9/11 16:18
 * @since 1.0.0
 */
@Slf4j
public class CheckResponseDTOJsonDeserializer extends JsonDeserializer<CheckResponseDTO> {

    @Override
    public CheckResponseDTO deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        try {
            final boolean isPass = node.get("isPass").asBoolean();
            log.error("检查接口调用链路 isPass = {}", isPass);

            CheckResponseDTO result = new CheckResponseDTO(isPass);

            final JsonNode errorMessageNode = node.get("errorMessage");
            if (errorMessageNode != null) {
                result.setErrorMessage(errorMessageNode.asText());
                log.error("检查接口调用链路 errorMessage = {}", errorMessageNode.asText());
            }

            final JsonNode checkTypeEnumNode = node.get("checkTypeEnum");
            if (checkTypeEnumNode != null) {
                final CheckTypeEnum checkTypeEnum = CheckTypeEnum.valueOf(checkTypeEnumNode.asText());
                result.setCheckTypeEnum(checkTypeEnum);
                log.error("检查接口调用链路 checkTypeEnumStr = {}", checkTypeEnum.name());
            }

            final JsonNode resultDTONode = node.get("resultDTO");
            log.error("resultDTONode = {}", resultDTONode);
            if (resultDTONode != null && result.getCheckTypeEnum() != null) {
                final ResultDTO resultDTO = JSONUtil.deserializeObject(resultDTONode.toString(), result.getCheckTypeEnum().resultClazz);
                result.setResultDTO(resultDTO);
                log.error("检查接口调用链路 resultDTO = {}", JSON.toJSONString(resultDTO));
            }

            return result;
        } catch (Exception e) {
            log.error("检查接口调用链路异常：, 需要走降级服务" + e);
            throw new RuntimeException("检查接口调用链路异常：, 需要走降级服务");
        }
    }
}