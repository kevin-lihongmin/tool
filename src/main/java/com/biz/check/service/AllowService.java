package com.biz.check.service;

import com.biz.check.constant.SelectByEnum;
import com.biz.check.manager.component.AbstractAdaptFeignCheck;
import com.biz.check.dto.request.CheckRequestDTO;
import com.biz.check.dto.response.CheckResponseDTO;
import org.springframework.stereotype.Service;

/**
 * 允销检查
 * @author kevin
 * @date 2020/8/18 16:56
 * @since 1.0.0
 */
@Service
public class AllowService extends AbstractAdaptFeignCheck {

    @Override
    protected CheckResponseDTO callService(CheckRequestDTO checkRequestDTO) {
        return null;
    }

    @Override
    protected Boolean checkPass(CheckRequestDTO checkRequestDTO) {
        return null;
    }

    @Override
    public Boolean adaptCheckPass(String idOrCode, SelectByEnum selectByEnum) {
        return super.adaptCheckPass(idOrCode, selectByEnum);
    }

    @Override
    public CheckResponseDTO adaptCheck(String idOrCode, SelectByEnum selectByEnum) {
        return super.adaptCheck(idOrCode, selectByEnum);
    }
}
