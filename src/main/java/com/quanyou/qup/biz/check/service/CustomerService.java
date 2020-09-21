package com.quanyou.qup.biz.check.service;

import com.quanyou.qup.biz.check.client.CustomerFeignService;
import com.quanyou.qup.biz.check.dto.request.CheckRequestDTO;
import com.quanyou.qup.biz.check.dto.response.CheckResponseDTO;
import com.quanyou.qup.biz.check.dto.response.CustomerResultDTO;
import com.quanyou.qup.biz.check.manager.component.AbstractAdaptFeignCheck;
import com.quanyou.qup.core.util.ConvertUtil;
import com.quanyou.qup.mdm.feign.dto.RemoteCustomerMainDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.quanyou.qup.middle.common.enums.EnabledEnum.isEnabled;

/**
 *  经销商服务
 *
 * @author kevin
 * @date 2020/8/12 14:36
 * @since 1.0.0
 */
@Service
public class CustomerService extends AbstractAdaptFeignCheck {

    /**
     * 外部调用 经销商服务
     */
    private final CustomerFeignService customerFeignService;

    @Autowired
    public CustomerService(CustomerFeignService customerFeignService) {
        this.customerFeignService = customerFeignService;
    }

    @Override
    protected CheckResponseDTO callService(CheckRequestDTO checkRequestDTO) {
        RemoteCustomerMainDTO remoteCustomer = customerFeignService.getRemote(checkRequestDTO.getIdOrCode(), checkRequestDTO.getSelectByEnum());
        if (remoteCustomer == null) {
            return null;
        }
        CustomerResultDTO customerResultDTO = ConvertUtil.convertObject(CustomerResultDTO.class, remoteCustomer);
        return new CheckResponseDTO(isPass(remoteCustomer), customerResultDTO);
    }

    /**
     *  获取经销商明细信息
     * @param checkRequestDTO 经销商请求信息
     * @return 经销商明细信息
     */
    public Boolean checkPass(CheckRequestDTO checkRequestDTO) {
        RemoteCustomerMainDTO remoteCustomer = customerFeignService.getRemote(checkRequestDTO.getIdOrCode(), checkRequestDTO.getSelectByEnum());
        return isPass(remoteCustomer);
    }

    /**
     *  判断：是否验证通过
     * @param remoteCustomerDetailDTO 经销商信息
     * @return 是否验证通过
     */
    private static Boolean isPass(RemoteCustomerMainDTO remoteCustomerDetailDTO) {
        if (remoteCustomerDetailDTO == null) {
            return false;
        }
        return isEnabled(remoteCustomerDetailDTO.getStatus());
    }

}
