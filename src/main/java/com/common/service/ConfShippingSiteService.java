package com.common.service;

import com.quanyou.qup.config.service.feign.RemoteConfShippingSiteService;
import com.quanyou.qup.config.service.feign.dto.RemoteConfShippingSiteDTO;
import com.quanyou.qup.core.util.FeignUtil;
import com.quanyou.qup.core.util.StringUtil;
import com.quanyou.qup.mdm.feign.RemoteProductMaterialGroupService;
import com.quanyou.qup.mdm.feign.dto.RemoteProductMaterialGroupDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import java.util.List;
import java.util.Objects;

/**
 * 发货装运点配置服务
 *
 * @author fudeling
 * @date 2020/8/31 23:04
 */
@Slf4j
@RequiredArgsConstructor
@ConditionalOnClass(value = {RemoteConfShippingSiteService.class, RemoteProductMaterialGroupService.class})
public class ConfShippingSiteService extends CacheConfShippingSite {
    private final RemoteConfShippingSiteService confShippingSiteService;
    private final RemoteProductMaterialGroupService productMaterialGroupService;

    /**
     * 根据：配送中心编码，工厂编码， 物料组
     *
     * @param distributionCenterCode 配送中心编码
     * @param factoryCode            工厂编码
     * @param materialGroupCode      物料组
     * @return {@link RemoteConfShippingSiteDTO}
     */
    public RemoteConfShippingSiteDTO getConfShippingSite(String distributionCenterCode, String factoryCode, String materialGroupCode) {
        String key = String.join("_", distributionCenterCode, factoryCode, materialGroupCode);
        RemoteConfShippingSiteDTO confShippingSiteDTO = (RemoteConfShippingSiteDTO) this.read(key);
        if (confShippingSiteDTO != null) {
            return confShippingSiteDTO;
        }

        List<RemoteProductMaterialGroupDTO> productMaterialGroupDTOList = getMaterialGroupTreeListByCode(materialGroupCode);
        for (RemoteProductMaterialGroupDTO materialGroupDTO : productMaterialGroupDTOList) {
            key = String.join("_", distributionCenterCode, factoryCode, materialGroupDTO.getCode());
            confShippingSiteDTO = (RemoteConfShippingSiteDTO) this.read(key);
            if (Objects.nonNull(confShippingSiteDTO)) {
                break;
            }
        }

        key = String.join("_", distributionCenterCode, factoryCode);
        if (confShippingSiteDTO == null) {
            confShippingSiteDTO = (RemoteConfShippingSiteDTO) this.read(key);
        }
        return confShippingSiteDTO;
    }


    /**
     * 获取所有发货装运点信息
     */
    private void setCacheConfShippingSite() {
        List<RemoteConfShippingSiteDTO> confShippingSiteDTOList = getRemoteConfShippingSite();
        confShippingSiteDTOList.forEach(shippingSiteDTO -> {
            String materialGroupCode = shippingSiteDTO.getMaterialGroupCode();
            if (StringUtil.isEmpty(materialGroupCode)) {
                String key = String.join("_", shippingSiteDTO.getDistributionCenterCode(), shippingSiteDTO.getFactoryCode());
                this.write(key, shippingSiteDTO);
            } else {
                String key = String.join("_", shippingSiteDTO.getDistributionCenterCode(), shippingSiteDTO.getFactoryCode(),
                        materialGroupCode);
                this.write(key, shippingSiteDTO);
            }
        });
    }

    private List<RemoteConfShippingSiteDTO> getRemoteConfShippingSite() {
        return FeignUtil.getData(confShippingSiteService.list());
    }

    private List<RemoteProductMaterialGroupDTO> getMaterialGroupTreeListByCode(String materialGroupCode) {
        return FeignUtil.getData(productMaterialGroupService.queryMaterialGroupTreeListByCode(materialGroupCode));
    }


    /**
     * 业务线程
     */
    @Override
    public void refreshCacheInterval() {
        try {
            setCacheConfShippingSite();
            log.info("Setup the shipping site data successfully.");
        } catch (Exception e) {
            log.error("线程初始化发货装运点信息失败:{}", e.getMessage());
        }
    }
}
