package com.common.dto;

import lombok.Data;

import java.util.List;

/**
 * RequestConfShippingSiteDTO.java
 *
 * @author fudeling
 * @date 2020/9/1 00:16
 */
@Data
public class ConfShippingSiteDTO {
    private String distributionCenterCode;
    private String factoryCode;
    private List<String> packCodes;

    public ConfShippingSiteDTO() {
    }

    public ConfShippingSiteDTO(String distributionCenterCode, String factoryCode, List<String> packCodes) {
        this.distributionCenterCode = distributionCenterCode;
        this.factoryCode = factoryCode;
        this.packCodes = packCodes;
    }
}
