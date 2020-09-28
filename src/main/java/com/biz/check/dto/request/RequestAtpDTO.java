package com.biz.check.dto.request;

import com.biz.check.constant.AtpSelectByEnum;
import lombok.*;

import java.util.List;

/**
 *  atp 用量查询请求
 * @author kevin
 * @date 2020/8/18 15:38
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestAtpDTO {

    /**
     * 查询类型
     */
    private AtpSelectByEnum atpSelectByEnum;

    /**
     *  发货工厂: 只有独资办开单时该值不为空
     */
    private String factoryCode;

    /**
     * 销售订单类型编码
     */
    private String soTypeCode;

    /**
     * 接单组织
     */
    private String receiveOrgCode;

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

}
