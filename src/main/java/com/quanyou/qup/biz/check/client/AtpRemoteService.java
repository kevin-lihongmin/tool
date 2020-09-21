package com.quanyou.qup.biz.check.client;

import com.google.common.collect.Lists;
import com.quanyou.qup.biz.check.dto.PackageDTO;
import com.quanyou.qup.core.bean.base.ResponseEntity;
import com.quanyou.qup.core.collection.CollectionUtil;
import com.quanyou.qup.middle.client.esb.sap.SapClient;
import com.quanyou.qup.middle.client.esb.sap.dto.AtpRequestDTO;
import com.quanyou.qup.middle.common.performance.TimeConsume;
import com.quanyou.qup.so.center.feign.RemoteFreezeQtyService;
import com.quanyou.qup.so.center.feign.RemoteVsoAtpService;
import com.quanyou.qup.so.center.feign.dto.RemoteRequestVsoAtpDTO;
import com.quanyou.qup.so.center.feign.dto.RemoteResponseFreezeQtyDTO;
import com.quanyou.qup.so.center.feign.dto.RemoteResponseVsoAtpDTO;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  可用量查询服务
 * @author kevin
 * @date 2020/8/18 14:26
 * @since 1.0.0
 */
@Repository
@AllArgsConstructor
public class AtpRemoteService {

    /**
     * atp查询的 最大包件分页数
     */
    private static final int SAP_ATP_PAGE_SIZE = 1000;

    private final SapClient sapClient;

    /**
     * VSO 占用量
     */
    private final RemoteVsoAtpService remoteVsoAtpService;

    /**
     *  VSO货源调配占用量
     */
    private final RemoteFreezeQtyService freezeQtyService;

    /**
     * 中转 atp查询结果
     * @param request 需要查询的产品编码请求
     * @return 包件可用量（atp） {@link MultiKeyMap}的key为：包件编码、发货工厂、库存地
     */
    public MultiKeyMap<String, BigDecimal> getRemoteAtp(List<AtpRequestDTO> request) {
        // 查询请求构建
       /* List<AtpRequestDTO> collect = productCodeList.stream().map(productCode -> {
            AtpRequestDTO atpRequestDTO = new AtpRequestDTO();
            atpRequestDTO.setProductCode(productCode);
            atpRequestDTO.setFactoryCode(atpDTO.getFactoryCode());
            atpRequestDTO.setSupplyAllocation(atpDTO.getSupplyAllocation());
//            atpRequestDTO.setStoreLocationCode(atpDTO.getStoreLocationCode());
            return atpRequestDTO;
        }).collect(Collectors.toList());*/

        MultiKeyMap<String, BigDecimal> result = new MultiKeyMap<>();
        Lists.partition(request, SAP_ATP_PAGE_SIZE).forEach(part -> result.putAll(sapClient.getAtp(part)));
        return result;
    }

    /**
     * 查询Sap真实的atp库存
     * @param atpRequestList 查询请求
     * @return 根据地点分组后的库存库存
     */
    /*public MultiKeyMap<String, BigDecimal> getRemoteAtp(List<AtpRequestDTO> atpRequestList) {
        MultiKeyMap<String, BigDecimal> atp = sapClient.getAtp(atpRequestList);
        return atp;
    }*/

    /**
     *  vso 占用的atp 包件级别占用量
     * @param distributionCenter 配送中心
     * @param packageList 包件集合
     * @return 包件占用量
     */
    @TimeConsume
    public List<PackageDTO> getRemoteVsoAtp(String distributionCenter, List<String> packageList) {
        RemoteRequestVsoAtpDTO request = new RemoteRequestVsoAtpDTO();
        request.setDistributionCenterCode(distributionCenter);
        request.setProductCodes(packageList);

        ResponseEntity<List<RemoteResponseVsoAtpDTO>> listResponseEntity = remoteVsoAtpService.queryAtp(Lists.newArrayList(request));
        if (!listResponseEntity.isSuccess() || CollectionUtil.isEmpty(listResponseEntity.getData())) {
            return new ArrayList<>();
        }
        return listResponseEntity.getData().stream()
                .map(obj -> new PackageDTO(obj.getProductCode(), obj.getProductQuantity()))
                .collect(Collectors.toList());
    }

    /**
     * 获取货源调配占用量（包件级别）暂用
     * @return 包件级别占用量
     */
    @TimeConsume
    public List<PackageDTO> getRemoteFreeze() {
        ResponseEntity<List<RemoteResponseFreezeQtyDTO>> listResponseEntity = freezeQtyService.queryFreezeQty();
        if (!listResponseEntity.isSuccess() || CollectionUtil.isEmpty(listResponseEntity.getData())) {
            return new ArrayList<>(0);
        }
        return listResponseEntity.getData().stream()
                .map(obj -> new PackageDTO(obj.getProductCode(), obj.getFreezeQty()))
                .collect(Collectors.toList());
    }


}
