package com.quanyou.qup.biz.check.service;

import com.quanyou.qup.biz.check.client.AddresseeFeignService;
import com.quanyou.qup.biz.check.dto.request.CheckRequestDTO;
import com.quanyou.qup.biz.check.dto.response.AddresseeResultDTO;
import com.quanyou.qup.biz.check.dto.response.CheckResponseDTO;
import com.quanyou.qup.biz.check.manager.component.AbstractAdaptFeignCheck;
import com.quanyou.qup.core.util.ConvertUtil;
import com.quanyou.qup.core.util.StringUtil;
import com.quanyou.qup.mdm.feign.dto.RemoteChannelCusStoreDTO;
import com.quanyou.qup.mdm.feign.dto.RemoteCustomerAddressDTO;
import com.quanyou.qup.mdm.feign.dto.RemoteShipToPartyDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.quanyou.qup.middle.common.enums.EnabledEnum.isEnabled;

/**
 *   业务送达方服务
 * @author kevin
 * @date 2020/8/13 9:40
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class AddresseeService extends AbstractAdaptFeignCheck {

    private final AddresseeFeignService addresseeFeignService;


    @Override
    protected CheckResponseDTO callService(CheckRequestDTO checkRequestDTO) {
        RemoteChannelCusStoreDTO cusStoreDTO = addresseeFeignService.getRemoteCusStore(checkRequestDTO.getIdOrCode());

        AddresseeResultDTO addresseeResultDTO = ConvertUtil.convertObject(AddresseeResultDTO.class, cusStoreDTO);
        return new CheckResponseDTO(isPass(null), addresseeResultDTO);
    }

    @Override
    protected Boolean checkPass(CheckRequestDTO checkRequestDTO) {
        RemoteChannelCusStoreDTO cusStoreDTO = addresseeFeignService.getRemoteCusStore(checkRequestDTO.getIdOrCode());
        if (cusStoreDTO == null || !"1".equals(cusStoreDTO.getStatus()) || StringUtil.isBlank(cusStoreDTO.getCode())) {
            return false;
        }
        final RemoteShipToPartyDTO remoteShipToPart = addresseeFeignService.getRemoteShipToPart(cusStoreDTO.getCode());
        if (remoteShipToPart == null || !Objects.equals(remoteShipToPart.getStatus(), 1)) {
            return false;
        }
        return true;
    }

    /**
     *  判断：是否验证通过
     * @param remote 送达方信息
     * @return 是否验证通过
     */
    private static Boolean isPass(RemoteCustomerAddressDTO remote) {
        if (remote == null) {
            return false;
        }
        return isEnabled(remote.getAddrStatus());
    }

}
