package com.quanyou.qup.biz.check.service;

import com.quanyou.qup.biz.check.dto.request.CheckRequestAtpDTO;
import com.quanyou.qup.biz.check.dto.request.CheckRequestDTO;
import com.quanyou.qup.biz.check.dto.request.RequestAtpDTO;
import com.quanyou.qup.biz.check.dto.response.AtpInfoResultDTO;
import com.quanyou.qup.biz.check.dto.response.AtpResultDTO;
import com.quanyou.qup.biz.check.dto.response.CheckResponseDTO;
import com.quanyou.qup.biz.check.manager.AtpManagerService;
import com.quanyou.qup.biz.check.manager.component.AbstractCheckService;
import com.quanyou.qup.middle.common.performance.TimeConsume;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 *  可用量服务
 * @author kevin
 * @date 2020/8/18 14:25
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class AtpService extends AbstractCheckService {

    private final AtpManagerService atpManagerService;

    @Override
    protected CheckResponseDTO callService(CheckRequestDTO checkRequestDTO) {
        CheckRequestAtpDTO atpDTO = (CheckRequestAtpDTO)checkRequestDTO;
        List<AtpInfoResultDTO> result = atpManagerService.process(atpDTO);
        return new CheckResponseDTO(isPass(result), new AtpResultDTO(result));
    }

    @Override
    protected Boolean checkPass(CheckRequestDTO checkRequestDTO) {
        CheckRequestAtpDTO atpDTO = (CheckRequestAtpDTO)checkRequestDTO;
        List<AtpInfoResultDTO> result = atpManagerService.process(atpDTO);
        return isPass(result);
    }

    /**
     * 是否检查通过
     * @param result 结果集合
     * @return 是否能满足所有需求量
     */
    private Boolean isPass(List<AtpInfoResultDTO> result) {
        for (AtpInfoResultDTO atpInfoResultDTO : result) {
            BigDecimal quantity = atpInfoResultDTO.getQuantity() == null ? BigDecimal.ZERO : atpInfoResultDTO.getQuantity();
            if (BigDecimal.valueOf(atpInfoResultDTO.getAvailableQuantity()).subtract(quantity).compareTo(BigDecimal.ZERO) < 0) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 适配查询可用量是否够用， 并且返回所有信息
     * @param atpDTO 可用量查询请求
     * @return 最新可用量信息，和计算结果
     */
    @TimeConsume
    public List<AtpInfoResultDTO> adaptCheck(RequestAtpDTO atpDTO) {
        CheckRequestAtpDTO request = new CheckRequestAtpDTO(atpDTO);
        return atpManagerService.process(request);
    }

    /**
     * 适配查询可用量是否够用
     * @param atpDTO 可用量查询请求
     * @return 是否验证通过
     */
    @TimeConsume
    public Boolean adaptCheckPass(RequestAtpDTO atpDTO) {
        return checkPass(new CheckRequestAtpDTO(atpDTO));
    }

}
