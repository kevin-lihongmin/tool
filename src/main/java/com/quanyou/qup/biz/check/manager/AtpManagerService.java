package com.quanyou.qup.biz.check.manager;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.quanyou.qup.biz.check.client.AtpRemoteService;
import com.quanyou.qup.biz.check.client.ConfigServiceFeignService;
import com.quanyou.qup.biz.check.dto.PackageDTO;
import com.quanyou.qup.biz.check.dto.request.AtpInfoDTO;
import com.quanyou.qup.biz.check.dto.request.CheckRequestAtpDTO;
import com.quanyou.qup.biz.check.dto.response.AtpInfoResultDTO;
import com.quanyou.qup.biz.check.manager.bo.PackageBO;
import com.quanyou.qup.config.service.feign.dto.RemoteConfShippingSiteDTO;
import com.quanyou.qup.core.collection.CollectionUtil;
import com.quanyou.qup.core.util.StringUtil;
import com.quanyou.qup.middle.client.esb.sap.dto.AtpRequestDTO;
import com.quanyou.qup.middle.common.Manager;
import com.quanyou.qup.middle.common.bean.ProductStruct;
import com.quanyou.qup.middle.common.bean.ProductStructRequest;
import com.quanyou.qup.middle.common.enums.SkuTypeEnum;
import com.quanyou.qup.middle.common.performance.TimeConsume;
import com.quanyou.qup.middle.common.service.ConfShippingSiteService;
import com.quanyou.qup.middle.common.service.ProductStructService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.quanyou.qup.biz.check.constant.SelectByEnum.ATP_SO;
import static com.quanyou.qup.middle.common.enums.ProductStructEnum.PACK_STRUCT;

/**
 *  atp 业务逻辑处理
 * @author kevin
 * @date 2020/9/8 10:01
 * @since 1.0.0
 */
@Slf4j
@Manager
public class AtpManagerService implements InitializingBean {

    private final AtpRemoteService atpRemoteService;

    private final ConfigServiceFeignService configServiceFeignService;

    private final ConfShippingSiteService confShippingSiteService;

    /**
     * sku和产品统一结构查询服务
     */
    private final ProductStructService productStructService;

    public AtpManagerService(AtpRemoteService atpRemoteService, ConfigServiceFeignService configServiceFeignService, ConfShippingSiteService confShippingSiteService, ProductStructService productStructService) {
        this.atpRemoteService = atpRemoteService;
        this.configServiceFeignService = configServiceFeignService;
        this.confShippingSiteService = confShippingSiteService;
        this.productStructService = productStructService;
    }

    /**
     * 存放 需要查询库存地的 发货工厂集合
     */
    private Set<String> configSet;

    /**
     * 需要查询转运
     */
    @Value(value = "config.atp.factory-codes-need-store-location")
    private String factoryCacheConfig;

    @Override
    public void afterPropertiesSet() {
        if (StringUtil.isBlank(factoryCacheConfig)) {
            throw new RuntimeException("系统配置信息有误！");
        }
        Set<String> temp = new HashSet<>();
        for (String factory : factoryCacheConfig.split(",")) {
            if (StringUtil.isNotBlank(factory)) {
                temp.add(factory);
            }
        }
        configSet = Collections.unmodifiableSet(temp);
    }

    /**
     * 处理可用量计算， 能满足的量
     * @param atpDTO atp请求
     * @return 每个sku 可用量信息
     */
    @TimeConsume
    public List<AtpInfoResultDTO> process(CheckRequestAtpDTO atpDTO) {
        // 构建统一结构查询请求
        ProductStructRequest request = buildStructRequest(atpDTO);
        // 使用公共方法查询所有的层级结构
        List<ProductStruct> productStruct = productStructService.listProductStruct(request);
        Map<String, ProductStruct> structMap = productStruct.stream().collect(Collectors.toMap(ProductStruct::getCode, struct -> struct));
        // 递归获取所有的包件
        Set<String> materialSet = Sets.newHashSet();
        ArrayList<PackageBO> packageBOList = Lists.newArrayList();
        ArrayList<String> packageList = Lists.newArrayList();
        getPackCodeList(productStruct, packageBOList, packageList, materialSet);

        final Map<String, String> keyMap = getFactoryAndStoreLocationIfNecessary(atpDTO, materialSet);
        // 获取包件Sap可用量
        Map<String, BigDecimal> atpMap = getAtpMap(atpDTO, packageBOList, keyMap);
        // 查询vso占用库存
        if (atpDTO.getSelectByEnum() == ATP_SO) {
            List<PackageDTO> remoteVsoAtp = atpRemoteService.getRemoteVsoAtp(atpDTO.getDistributionCenter(), packageList);
            subtract(atpMap, remoteVsoAtp);
        }
        // 查询调配占用库存
        List<PackageDTO> remoteFreeze = atpRemoteService.getRemoteFreeze();
        subtract(atpMap, remoteFreeze);

        // 查询sku 关联的 包件结构
        final ArrayList<AtpInfoResultDTO> result = Lists.newArrayListWithCapacity(atpDTO.getAtpInfoDTOList().size());
        atpDTO.getAtpInfoDTOList().forEach(atpInfoDTO -> {
            ProductStruct struct = structMap.get(atpInfoDTO.code);
            if (atpInfoDTO.getTypeEnum() == SkuTypeEnum.COMBINE) {
                // 如果是组合需要单独计算下面的sku的量(组合的量还是要计算的)
                struct.getChildren().forEach(structSku -> {
                    AtpInfoResultDTO atpInfoResultDTO = new AtpInfoResultDTO(structSku.getCode(), 0);
                    calculateMinNum(structSku, atpMap, 1, atpInfoResultDTO);
                    result.add(atpInfoResultDTO);
                });
            }
            AtpInfoResultDTO baseAtpDTO = new AtpInfoResultDTO(atpInfoDTO, 0);
            calculateMinNum(struct, atpMap, 1, baseAtpDTO);
            result.add(baseAtpDTO);
        });

        return result;
    }

    /**
     * 递归计算最小可用量
     * @param struct 当前统一结构
     * @param atpMap 真正能使用的包件可用量
     * @param baseNum 当前层级最小需要数量
     * @param atpInfoResultDTO 主要是传递对象，修改能组成sku（或sku组合等）的最小执值
     */
    private void calculateMinNum(ProductStruct struct, Map<String, BigDecimal> atpMap, Integer baseNum, AtpInfoResultDTO atpInfoResultDTO) {
        if (struct.getType() == PACK_STRUCT && CollectionUtil.isEmpty(struct.getChildren())) {
            BigDecimal bigDecimal = atpMap.get(struct.getCode());
            // 组合单个sku 或者组合需要的最小当前包件数量
            int baseAllNeed = struct.getNum() * baseNum;
            int num = bigDecimal.divide(BigDecimal.valueOf(baseAllNeed)).intValue();
            if (num > 0 && (atpInfoResultDTO.availableQuantity == 0 || num < atpInfoResultDTO.availableQuantity)) {
                atpInfoResultDTO.availableQuantity = num;
            }
        } else {
            for (ProductStruct childStruct : struct.getChildren()) {
                calculateMinNum(childStruct, atpMap, childStruct.getNum() * baseNum, atpInfoResultDTO);
            }
        }
    }

    /**
     * 组装工厂和仓库信息， 如果必要（调用者没有传）
     * @param atpDTO 请求参数
     * @param materialSet 物料组集合
     * @return 包件与仓库的关系
     */
    private Map<String, String> getFactoryAndStoreLocationIfNecessary(CheckRequestAtpDTO atpDTO, Set<String> materialSet) {
        final Map<String, String> keyMap = new HashMap<>(16);
        if (StringUtil.isBlank(atpDTO.getFactoryCode())) {
            // 查询(配送中心 + 接单组织 + 订单类型 = 工厂) 工厂配置表
            String factoryCode = configServiceFeignService.getRemoteFactoryCode(atpDTO.getSoTypeCode(), atpDTO.getReceiveOrgCode(), atpDTO.getDistributionCenter());
            atpDTO.setFactoryCode(factoryCode);

            if (configSet.contains(factoryCode)) {
                // 查找物料组（父物料组）对应的 仓库
                keyMap.putAll(materialSet.stream().collect(Collectors.toMap(material -> material, material -> {
                    RemoteConfShippingSiteDTO confShippingSite = confShippingSiteService.getConfShippingSite(atpDTO.getDistributionCenter(), factoryCode, material);
                    return confShippingSite.getStoreLocationCode();
                })));
            }
        }
        return keyMap;
    }

    /**
     *  获取包件Sap可用量
     * @param atpDTO 请求参数
     * @param packageBOList 包件业务实体集合
     * @param keyMap 包件对应的仓库
     * @return 包件Sap可用量
     */
    @TimeConsume
    private Map<String, BigDecimal> getAtpMap(CheckRequestAtpDTO atpDTO, ArrayList<PackageBO> packageBOList, Map<String, String> keyMap) {
        // 查询请求构建
        List<AtpRequestDTO> requestAtp = packageBOList.stream().map(packageBO -> {
            AtpRequestDTO atpRequestDTO = new AtpRequestDTO();
            atpRequestDTO.setProductCode(packageBO.packageCode);
            atpRequestDTO.setFactoryCode(atpDTO.getFactoryCode());
            atpRequestDTO.setSupplyAllocation(atpDTO.getSupplyAllocation());
            if (configSet.contains(atpDTO.getFactoryCode())) {
                if (StringUtil.isNotBlank(atpDTO.getStoreLocationCode())) {
                    atpRequestDTO.setStoreLocationCode(atpDTO.getStoreLocationCode());
                } else {
                    atpRequestDTO.setStoreLocationCode(keyMap.get(packageBO.materialGroupCode));
                }
            }
            return atpRequestDTO;
        }).collect(Collectors.toList());

        // 查询总库存
        MultiKeyMap<String, BigDecimal> remoteAtp = atpRemoteService.getRemoteAtp(requestAtp);

        // 组装包件可用量量Map
        return requestAtp.stream().collect(Collectors.toMap(AtpRequestDTO::getProductCode, atpRequestDTO -> {
                    if (StringUtil.isNotBlank(atpRequestDTO.getStoreLocationCode())) {
                        return remoteAtp.get(atpRequestDTO.getProductCode(), atpRequestDTO.getFactoryCode(), atpRequestDTO.getStoreLocationCode());
                    } else {
                        return remoteAtp.get(atpRequestDTO.getProductCode(), atpRequestDTO.getFactoryCode());
                    }
                }
        ));
    }

    /**
     * 减去对应的数量
     * @param atpMap atp包件可用量
     * @param subtractList 需要减的包件数量
     */
    private void subtract(Map<String, BigDecimal> atpMap, List<PackageDTO> subtractList) {
        if (CollectionUtil.isNotEmpty(subtractList)) {
            subtractList.forEach(packageDTO -> {
                BigDecimal bigDecimal = atpMap.get(packageDTO.packageCode);
                if (bigDecimal != null) {
                    atpMap.replace(packageDTO.packageCode, bigDecimal.subtract(packageDTO.atp));
                }
            });
        }
    }

    /**
     * 构建查询统一结构请求
     * @param atpDTO atp查询请求
     * @return 统一结构查询请求
     */
    private ProductStructRequest buildStructRequest(CheckRequestAtpDTO atpDTO) {
        ProductStructRequest request = new ProductStructRequest();
        Map<SkuTypeEnum, List<AtpInfoDTO>> collect = atpDTO.getAtpInfoDTOList().stream().collect(Collectors.groupingBy(atp -> atp.typeEnum));
        if (CollectionUtil.isNotEmpty(collect.get(SkuTypeEnum.COMBINE))) {
            request.setCombinationCodes(collect.get(SkuTypeEnum.COMBINE).stream().map(info -> info.code).collect(Collectors.toList()));
        }
        if (CollectionUtil.isNotEmpty(collect.get(SkuTypeEnum.SKU))) {
            request.setSkuCodes(collect.get(SkuTypeEnum.SKU).stream().map(info -> info.code).collect(Collectors.toList()));
        }
        if (CollectionUtil.isNotEmpty(collect.get(SkuTypeEnum.SINGLE_PACK))) {
            request.setProductCodes(collect.get(SkuTypeEnum.SINGLE_PACK).stream().map(info -> info.code).collect(Collectors.toList()));
        }
        return request;
    }

    /**
     * 递归获取所有的包件
     * @param productStruct 统一结构
     * @param packageBOList 包件业务实体容器
     * @param packageList 包件容器
     */
    private void getPackCodeList(List<ProductStruct> productStruct, ArrayList<PackageBO> packageBOList,
                                 ArrayList<String> packageList, Set<String> materialSet) {
        productStruct.forEach(struct -> {
            if (struct.getType() == PACK_STRUCT && CollectionUtil.isEmpty(struct.getChildren())) {
                packageList.add(struct.getCode());
                materialSet.add(struct.getProductInfo().getMaterialGroupCode());
                packageBOList.add(new PackageBO(struct.getCode(), struct.getProductInfo().getMaterialGroupCode()));
            } else {
                getPackCodeList(struct.getChildren(), packageBOList, packageList, materialSet);
            }
        });
    }

}
