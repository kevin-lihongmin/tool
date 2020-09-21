package com.quanyou.qup.biz.check.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.quanyou.qup.biz.check.config.CheckChainDTOJsonDeserializer;
import com.quanyou.qup.biz.check.constant.CheckTypeEnum;
import com.quanyou.qup.middle.common.enums.BooleanEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  检查项
 * @author kevin
 * @date 2020/8/12 10:18
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = CheckChainDTOJsonDeserializer.class)
public class CheckChainDTO {

    /**
     * 验证类型
     */
    private CheckTypeEnum checkTypeEnum;

    /**
     * 是否需要返回查询的信息， 为调用返回后使用， 避免二次查询数据
     * 默认为【否】
     */
    private BooleanEnum getResult = BooleanEnum.NO;

    /**
     * 入参
     */
    private CheckRequestDTO checkRequestDTO;

    public CheckChainDTO(CheckRequestDTO checkRequestDTO) {
        this.checkRequestDTO = checkRequestDTO;
        getResult = BooleanEnum.YES;
    }
}
