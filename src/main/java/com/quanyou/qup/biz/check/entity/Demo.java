package com.quanyou.qup.biz.check.entity;

import com.quanyou.qup.core.bean.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Demo
 *
 * @author 抓抓匠
 * @since 2020-06-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Demo extends BaseEntity {
    private static final long serialVersionUID = 659423064052587280L;

    /**
     * ID
     */
    private String id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型ID
     */
    private String typeId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 数据状态
     */
    private Integer dataStatus;
}