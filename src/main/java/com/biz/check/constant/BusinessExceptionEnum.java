package com.biz.check.constant;

/**
 *  业务异常定义
 * @author kevin
 * @date 2020/8/11 16:13
 * @since 1.0.0
 */
public enum BusinessExceptionEnum {

    PARAMETER_EXCEPTION("查询参数异常"),
    PARAMETER_MATCH_WITH_INDEX_EXCEPTION("查询参数类型异常【查询项 %d 类型 %s 与入参不匹配】"),
    PARAMETER_MATCH_EXCEPTION("查询参数类型异常【查询项类型 %s 与入参不匹配】"),
    SEARCH_EXCEPTION("查询参数异常"),
    PARAMETER_EMPTY_EXCEPTION("查询参数不能为空");

    public String message;

    BusinessExceptionEnum(String message) {
        this.message = message;
    }

    /**
     *  抛出业务异常
     * @param exceptionEnum 异常枚举
     * @return 异常信息
     *//*
    public static void buildException(BusinessExceptionEnum exceptionEnum) throws BusinessException {
        throw new BusinessException(exceptionEnum.message);
    }

    *//**
     *  抛出业务异常
     * @param exceptionEnum 需要字符替换的错误枚举
     * @param info 需要替换掉的信息
     * @return 异常信息
     *//*
    public static void buildException(BusinessExceptionEnum exceptionEnum, Object... info) throws BusinessException {
        throw new BusinessException(String.format(exceptionEnum.message, info));
    }*/

}
