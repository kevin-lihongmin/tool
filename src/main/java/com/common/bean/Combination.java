package com.common.bean;

import lombok.Data;

/**
 * 组合
 *
 * @author 抓抓匠
 * @since 2020-08-26
 */
@Data
public class Combination {
    /**
     * 主键
     */
    private Long id;

    /**
     * 产品组合编码
     */
    private String code;

    /**
     * 产品组合名称
     */
    private String name;
}
