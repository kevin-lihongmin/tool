package com.quanyou.qup.biz.check.controller.provider.v1;

import com.quanyou.qup.biz.check.dto.request.RequestAtpDTO;
import com.quanyou.qup.biz.check.dto.response.AtpInfoResultDTO;
import com.quanyou.qup.biz.check.dto.response.AtpResultDTO;
import com.quanyou.qup.biz.check.dto.response.CheckResponseDTO;
import com.quanyou.qup.biz.check.service.AtpService;
import com.quanyou.qup.core.bean.base.ResponseEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.quanyou.qup.biz.check.constant.AtpSelectByEnum.ATP_SO;

/**
 *  可用量 相关检查
 *
 * @author kevin
 * @date 2020/8/11 13:21
 * @since 1.0.0
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/provider/v1/atp")
public class ProviderAtpController {

    private final AtpService atpService;

    /**
     *  获取可用量明细信息
     * @param atpDTO 可用量请求
     * @return 可用量明细信息
     */
    @PostMapping("getAtpAndCheck")
    public ResponseEntity<List<AtpInfoResultDTO>> getAtpAndCheck(@RequestBody RequestAtpDTO atpDTO) {
        List<AtpInfoResultDTO> result = atpService.adaptCheck(atpDTO);
        return ResponseEntity.success(result);
    }

    /**
     *  验证可用量信息
     * @param atpDTO 可用量请求
     * @return 可用量验证是否通过
     */
    @PostMapping("checkAtp")
    public ResponseEntity<Boolean> checkAtp(@RequestBody RequestAtpDTO atpDTO) {
        Boolean isNatural = atpService.adaptCheckPass(atpDTO);
        return ResponseEntity.success(isNatural);
    }

    /**
     * 为可用量查询设置默认值
     * @param atpDTO 可用量查询请求
     */
    private void setDefaultValue(RequestAtpDTO atpDTO) {
        if (atpDTO.getAtpSelectByEnum() == null) {
            atpDTO.setAtpSelectByEnum(ATP_SO);
        }
    }

}