package com.quanyou.qup.biz.check.client;

import com.alibaba.fastjson.JSON;
import com.quanyou.qup.core.bean.base.ResponseEntity;
import com.quanyou.qup.mdm.feign.RemoteChannelCusStoreService;
import com.quanyou.qup.mdm.feign.RemoteShipToPartyService;
import com.quanyou.qup.mdm.feign.dto.RemoteChannelCusStoreDTO;
import com.quanyou.qup.mdm.feign.dto.RemoteShipToPartyDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 *  远程：送达方服务
 * @author kevin
 * @date 2020/8/13 9:36
 * @since 1.0.0
 */
@Slf4j
@Repository
@AllArgsConstructor
public class AddresseeFeignService {

    private final RemoteChannelCusStoreService cusStoreService;

    private final RemoteShipToPartyService shipToPartyService;

    /**
     *
     * @param idOrCode
     * @return
     */
    public RemoteChannelCusStoreDTO getRemoteCusStore(String idOrCode) {
        ResponseEntity<RemoteChannelCusStoreDTO> response = cusStoreService.getById(idOrCode);
        log.info("get remote address = {}", JSON.toJSONString(response));
        if (!response.isSuccess()) {
            return null;
        }
        return response.getData();
    }

    /**
     *
     * @param code
     * @return
     */
    public RemoteShipToPartyDTO getRemoteShipToPart(String code) {
        final ResponseEntity<RemoteShipToPartyDTO> response = shipToPartyService.getByCode(code);
        if (!response.isSuccess()) {
            return null;
        }
        return response.getData();
    }

}
