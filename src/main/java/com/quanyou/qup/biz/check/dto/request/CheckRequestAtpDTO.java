package com.quanyou.qup.biz.check.dto.request;

import com.quanyou.qup.biz.check.constant.SelectByEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 *  Atp 检查请求
 *
 *  单独开销售订单、货源安排调用时：{@link #selectByEnum} 类型为 {@link SelectByEnum#ATP_SO}
 *  vso转so、独资办开单调用时：{@link #selectByEnum} 类型为{@link SelectByEnum#ATP_NO_VSO}
 *
 *
 * @author kevin
 * @date 2020/8/18 15:19
 * @since 1.0.0
 * @see SelectByEnum#ATP_SO
 * @see SelectByEnum#ATP_NO_VSO
 */
@Getter
@Setter
@NoArgsConstructor
public class CheckRequestAtpDTO extends CheckRequestDTO {

    /*-----------------单独开销售订单，该参数不能为空-------------*/
    /**
     *  发货工厂
     */
    private String factoryCode;

    /**
     * 仓库
     */
    private String storeLocationCode;

    /*-----------------非单独开单需要传------------------------*/
    /**
     * 接单组织
     */
    private String receiveOrgCode;

    /*-----------------公用必传参数---------------------------*/
    /**
     * 销售订单类型编码
     */
    private String soTypeCode;

    /**
     *  货源调配专用标记，非货源调配查询无需传此参数
     */
    private String supplyAllocation;

    /**
     *  配送中心
     */
    private String distributionCenter;

    /**
     * sku 编码集合 和 需求量
     */
    private List<AtpInfoDTO> atpInfoDTOList;

    public CheckRequestAtpDTO(SelectByEnum selectByEnum, String factoryCode, String storeLocationCode, String supplyAllocation,
                              String distributionCenter,  List<AtpInfoDTO> atpInfoDTOList) {
        super(null, selectByEnum);
        this.factoryCode = factoryCode;
        this.supplyAllocation = supplyAllocation;
        this.distributionCenter = distributionCenter;
        this.atpInfoDTOList = atpInfoDTOList;
    }

    public CheckRequestAtpDTO(SelectByEnum selectByEnum) {
        super(null, selectByEnum);
    }

    public CheckRequestAtpDTO(RequestAtpDTO atpDTO) {
        super(null, SelectByEnum.valueOf(atpDTO.getAtpSelectByEnum() + ""));
        this.receiveOrgCode = atpDTO.getReceiveOrgCode();
        this.soTypeCode = atpDTO.getSoTypeCode();
        this.factoryCode = atpDTO.getFactoryCode();
        this.supplyAllocation = atpDTO.getSupplyAllocation();
        this.distributionCenter = atpDTO.getDistributionCenter();
        this.atpInfoDTOList = atpDTO.getAtpInfoDTOList();
    }
}
