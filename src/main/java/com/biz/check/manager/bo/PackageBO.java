package com.biz.check.manager.bo;

import lombok.AllArgsConstructor;

/**
 *  包件业务实体
 * @author kevin
 * @date 2020/8/31 11:03
 * @since 1.0.0
 */
@AllArgsConstructor
public final class PackageBO {

    /**
     * 包件编码
     */
    public final String packageCode;

    /**
     * 物料组编码
     */
    public final String materialGroupCode;
}
