package com.quanyou.qup.biz.check.client;

import com.quanyou.qup.biz.check.constant.SelectByEnum;
import com.quanyou.qup.core.bean.base.ResponseEntity;
import com.quanyou.qup.mdm.feign.RemoteCustomerService;
import com.quanyou.qup.mdm.feign.dto.RemoteCustomerMainDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 *  外部：经销商服务
 *
 * @author kevin
 * @date 2020/8/11 15:16
 * @since 1.0.0
 */
@Repository
@AllArgsConstructor
public class CustomerFeignService {

    private final RemoteCustomerService remoteCustomerService;

    /**
     *  获取经销商明细信息
     * @param idOrCode 主键或编码
     * @param selectByEnum 查询类型枚举
     * @return 经销商明细信息
     */
    public RemoteCustomerMainDTO getRemote(String idOrCode, SelectByEnum selectByEnum) {
        ResponseEntity<RemoteCustomerMainDTO> response;
        if (selectByEnum == SelectByEnum.ID) {
            response = remoteCustomerService.getMainById(idOrCode);
        } else {
            response = remoteCustomerService.getMainByCode(idOrCode);
        }
        if (!response.isSuccess()) {
            return null;
        }
        return response.getData();
    }


}
