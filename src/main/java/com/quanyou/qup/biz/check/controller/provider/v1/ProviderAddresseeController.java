package com.quanyou.qup.biz.check.controller.provider.v1;

import com.quanyou.qup.biz.check.constant.SelectByEnum;
import com.quanyou.qup.biz.check.dto.response.CheckResponseDTO;
import com.quanyou.qup.biz.check.service.AddresseeService;
import com.quanyou.qup.core.bean.base.ResponseEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.quanyou.qup.biz.check.controller.provider.v1.ValidCheckDelegate.validId;

/**
 *  送达方相关检查
 *
 * @author kevin
 * @date 2020/8/11 13:21
 * @since 1.0.0
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/provider/v1/addressee")
public class ProviderAddresseeController {

    private final AddresseeService addresseeService;

    /**
     *  获取送达方明细信息
     * @param idOrCode 主键
     * @return 送达方明细信息
     */
    @GetMapping("getAddresseeAndCheck")
    public ResponseEntity<CheckResponseDTO> getAddresseeAndCheck(@RequestParam("idOrCode") String idOrCode,
                                                                 @RequestParam("selectByEnum") SelectByEnum selectByEnum) {
        validId(idOrCode);
        if (selectByEnum == null) {
            selectByEnum = SelectByEnum.ID;
        }
        CheckResponseDTO customer = addresseeService.adaptCheck(idOrCode, selectByEnum);
        return ResponseEntity.success(customer);
    }

    /**
     *  验证送达方信息
     * @param idOrCode 主键或编码
     * @param selectByEnum 查询类型枚举
     * @return 送达方状态是否正常
     */
    @GetMapping("checkAddressee")
    public ResponseEntity<Boolean> checkAddressee(@RequestParam("idOrCode") String idOrCode,
                                                  @RequestParam("selectByEnum") SelectByEnum selectByEnum) {
        validId(idOrCode);
        if (selectByEnum == null) {
            selectByEnum = SelectByEnum.ID;
        }
        Boolean isNatural = addresseeService.adaptCheckPass(idOrCode, selectByEnum);
        return ResponseEntity.success(isNatural);
    }

}