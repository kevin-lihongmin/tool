package com.quanyou.qup.biz.check.controller.provider.v1;

import com.alibaba.fastjson.JSON;
import com.quanyou.qup.biz.check.dto.request.CheckChainDTO;
import com.quanyou.qup.biz.check.dto.response.CheckResultDTO;
import com.quanyou.qup.biz.check.service.CheckService;
import com.quanyou.qup.core.bean.base.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *  经销商相关检查，包括业务账号
 *
 * @author kevin
 * @date 2020/8/11 13:21
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/provider/v1/check")
public class ProviderCheckController {

    private final CheckService checkService;

    @Autowired
    public ProviderCheckController(CheckService checkService) {
        this.checkService = checkService;
    }

    /**
     *  检查链路查询
     * @param checkChains 检查请求项
     * @return 按需范围的检查结果列表
     */
    @PostMapping("checkChain")
    public ResponseEntity<CheckResultDTO> checkChain(@RequestBody CheckChainDTO... checkChains) {
        ValidCheckDelegate.valid(checkChains);
        CheckResultDTO resultDTO = checkService.checkChainProcess(checkChains);
        log.error("检查链调用结果 {}", JSON.toJSONString(resultDTO));
        return ResponseEntity.success(resultDTO);
    }

}