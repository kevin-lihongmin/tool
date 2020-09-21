package com.quanyou.qup.biz.check.service;

import com.quanyou.qup.biz.check.client.AccountFeignService;
import com.quanyou.qup.biz.check.dto.request.CheckRequestDTO;
import com.quanyou.qup.biz.check.dto.response.AccountResultDTO;
import com.quanyou.qup.biz.check.dto.response.CheckResponseDTO;
import com.quanyou.qup.biz.check.manager.component.AbstractAdaptFeignCheck;
import com.quanyou.qup.core.util.ConvertUtil;
import com.quanyou.qup.mdm.feign.dto.RemoteAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.quanyou.qup.middle.common.enums.EnabledEnum.isEnabled;

/**
 *  业务账号服务
 * @author kevin
 * @date 2020/8/13 9:13
 * @since 1.0.0
 */
@Service
public class AccountService extends AbstractAdaptFeignCheck {

    private final AccountFeignService accountFeignService;

    @Autowired
    public AccountService(AccountFeignService accountFeignService) {
        this.accountFeignService = accountFeignService;
    }

    @Override
    protected CheckResponseDTO callService(CheckRequestDTO checkRequestDTO) {
        RemoteAccountDTO remote = accountFeignService.getRemote(checkRequestDTO.getIdOrCode(), checkRequestDTO.getSelectByEnum());
        if (remote == null) {
            return null;
        }
        AccountResultDTO accountResultDTO = ConvertUtil.convertObject(AccountResultDTO.class, remote);
        return new CheckResponseDTO(isPass(remote), accountResultDTO);
    }

    @Override
    protected Boolean checkPass(CheckRequestDTO checkRequestDTO) {
        RemoteAccountDTO remote = accountFeignService.getRemote(checkRequestDTO.getIdOrCode(), checkRequestDTO.getSelectByEnum());
        return isPass(remote);
    }

    /**
     *  判断：是否验证通过
     * @param remote 经营账号信息
     * @return 是否验证通过
     */
    private static Boolean isPass(RemoteAccountDTO remote) {
        if (remote == null) {
            return false;
        }
        return isEnabled(remote.getStatus()) /*&& isEnabled(remote.getStatus())*/;
    }
}
