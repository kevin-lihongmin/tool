package com.quanyou.qup.biz.check.manager.component;

import com.quanyou.qup.biz.check.dto.request.CheckChainDTO;
import com.quanyou.qup.biz.check.dto.request.CheckRequestDTO;
import com.quanyou.qup.biz.check.dto.response.CheckResponseDTO;
import com.quanyou.qup.middle.common.enums.BooleanEnum;
import lombok.extern.slf4j.Slf4j;

/**
 *  检查服务抽象
 * @author kevin
 * @date 2020/8/12 13:35
 * @since 1.0.0
 */
@Slf4j
public abstract class AbstractCheckService {

    /**
     *  检查服务项 [模板方法]
     *
     * @param checkChainDTO 检查请求
     * @return 条件匹配的结果
     */
    public CheckResponseDTO check(CheckChainDTO checkChainDTO) {
        if (BooleanEnum.yes(checkChainDTO.getGetResult())) {
            CheckResponseDTO checkResponseDTO = callService(checkChainDTO.getCheckRequestDTO());
            checkResponseDTO.setCheckTypeEnum(checkChainDTO.getCheckTypeEnum());
            return checkResponseDTO;
        } else {
            Boolean isPass = checkPass(checkChainDTO.getCheckRequestDTO());
            log.info("valid {} reault = {}", checkChainDTO.getCheckTypeEnum(), isPass);
            return new CheckResponseDTO(isPass);
        }
    }

    /**
     *  获取执行结果
     * @param checkRequestDTO 检查请求
     * @return 检查结果
     */
    protected abstract CheckResponseDTO callService(CheckRequestDTO checkRequestDTO);

    /**
     *  是否验证通
     * @param checkRequestDTO 请求入参
     * @return 是否通过
     */
    protected abstract Boolean checkPass(CheckRequestDTO checkRequestDTO);

}
