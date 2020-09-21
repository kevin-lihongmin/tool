package com.quanyou.qup.biz.check.feign.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 *  验证链 结果
 * @author kevin
 * @date 2020/8/12 11:12
 * @since 1.0.0
 */
@Getter
@Setter
public class CheckResultDTO {

    /**
     * 是否都验证通过
     */
    Boolean isTotalPass;

    /**
     * 检查结果项
     */
    List<CheckResponseDTO> resultChain;

    public CheckResultDTO(Boolean isTotalPass) {
        this.isTotalPass = isTotalPass;
    }

    public CheckResultDTO(Boolean isTotalPass, List<CheckResponseDTO> resultChain) {
        this.isTotalPass = isTotalPass;
        this.resultChain = resultChain;
    }

    public CheckResultDTO() {
        this.isTotalPass = null;
        this.resultChain = null;
    }

    /**
     *  添加结果， 非线程安全，串行调用
     * @param dto 结果项
     */
    public void addResponse(CheckResponseDTO dto) {
        if (resultChain == null) {
            // 使用默认长度 10
            resultChain = new ArrayList<>(10);
        }
        resultChain.add(dto);
    }

}
