package com.quanyou.qup.biz.check.dto.response;

import com.quanyou.qup.biz.check.constant.CheckTypeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 *  检查结果抽象
 * @author kevin
 * @date 2020/8/12 10:20
 * @since 1.0.0
 */
@Getter
@Setter
public class CheckResponseDTO {

    /**
     * 当前检查项：是否验证通过
     */
    private Boolean isPass;

    /**
     * 检查失败原因
     */
    private String errorMessage;

    /**
     * 检查的类型
     */
    private CheckTypeEnum checkTypeEnum;

    /**
     * 检查返回实体
     */
    private ResultDTO resultDTO;

    public CheckResponseDTO(Boolean isPass, ResultDTO resultDTO) {
        this.isPass = isPass;
        this.resultDTO = resultDTO;
    }

    public CheckResponseDTO(Boolean isPass) {
        this.isPass = isPass;
    }
}
