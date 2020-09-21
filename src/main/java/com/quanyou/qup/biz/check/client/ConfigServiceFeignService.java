package com.quanyou.qup.biz.check.client;

import com.google.common.collect.Lists;
import com.quanyou.qup.config.service.feign.RemoteConfCreditScopeService;
import com.quanyou.qup.config.service.feign.RemoteConfDlvyFactoryService;
import com.quanyou.qup.config.service.feign.dto.FactoryCodeDTO;
import com.quanyou.qup.config.service.feign.dto.RemoteConfCreditScopeDTO;
import com.quanyou.qup.core.bean.base.ResponseEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  获取配置工厂
 * @author kevin
 * @date 2020/8/31 11:26
 * @since 1.0.0
 */
@Slf4j
@Repository
@AllArgsConstructor
public class ConfigServiceFeignService {

    private final RemoteConfDlvyFactoryService remoteConfDlvyFactoryService;

    private final RemoteConfCreditScopeService remoteConfCreditScopeService;

    /**
     * 获取发货工厂配置
     * @param soTypeCode 销售订单类型
     * @param receiveOrgCode 接单组织
     * @param distributionCenterCode 配送中心
     * @return 发货工厂编码
     */
    public String getRemoteFactoryCode(String soTypeCode, String receiveOrgCode, String distributionCenterCode) {

        FactoryCodeDTO request = new FactoryCodeDTO(soTypeCode, receiveOrgCode, distributionCenterCode);

        ResponseEntity<String> responseEntity = remoteConfDlvyFactoryService.getFactoryCode(request);
        if (!responseEntity.isSuccess()) {
            log.error("获取发货工厂异常");
            return "";
        }
        return responseEntity.getData();
    }

    /**
     * 获取全量的信贷范围数据
     * @return 全量数据
     */
    public List<RemoteConfCreditScopeDTO> getAllCreditScope() {
        ResponseEntity<List<RemoteConfCreditScopeDTO>> listResponseEntity = remoteConfCreditScopeService.listAllCreditScope();
        if (!listResponseEntity.isSuccess()) {
            return Lists.newArrayList();
        }
        return listResponseEntity.getData();
    }

}
