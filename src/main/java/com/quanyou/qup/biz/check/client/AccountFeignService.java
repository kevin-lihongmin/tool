package com.quanyou.qup.biz.check.client;

import com.quanyou.qup.biz.check.constant.SelectByEnum;
import com.quanyou.qup.core.bean.base.ResponseEntity;
import com.quanyou.qup.mdm.feign.RemoteAccountService;
import com.quanyou.qup.mdm.feign.dto.RemoteAccountDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 *  外部： 经营账号服务
 * @author kevin
 * @date 2020/8/13 8:53
 * @since 1.0.0
 */
@Repository
@AllArgsConstructor
public class AccountFeignService {

    private final RemoteAccountService remoteAccountService;

    public RemoteAccountDTO getRemote(String idOrCode, SelectByEnum selectByEnum) {
        ResponseEntity<RemoteAccountDTO> response;
        if (selectByEnum == SelectByEnum.ID) {
            response = remoteAccountService.getById(idOrCode);
        } else {
            response = remoteAccountService.getByCode(idOrCode);
        }
        if (!response.isSuccess()) {
            return null;
        }
        return response.getData();
    }

}
