package com.quanyou.qup.biz.check.dto;

import java.math.BigDecimal;

/**
 *  包件用量
 * @author kevin
 * @date 2020/8/18 15:07
 * @since 1.0.0
 */
public final class PackageDTO {

    /**
     * 包件编码
     */
    public final String packageCode;

    /**
     * Atp，或Atp占用量
     */
    public final BigDecimal atp;

    public PackageDTO(String packageCode, BigDecimal atp) {
        this.packageCode = packageCode;
        this.atp = atp;
    }
}
