package com.quanyou.qup.biz.check.dto.response;

import com.quanyou.qup.middle.common.enums.ProductStructEnum;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 *  sku 状态检查结果
 *
 *  下面两个集合要不都为空，要不都不为空
 *
 * @author kevin
 * @date 2020/8/28 16:01
 * @since 1.0.0
 */
@AllArgsConstructor
public final class SkuResultDTO extends ResultDTO {

    /**
     * 检验不通过的提示消息
     */
    public final List<List<ErrorTrace>> errorTraceList;

    /**
     * 状态不正常的（sku、sku组合、单包件）编码
     */
    public final Set<String> errorCodeSet;

    /**
     * 错误层级信息
     */
    @AllArgsConstructor
    public static final class ErrorTrace {

        /**
         * sku层级类型
         */
        public final ProductStructEnum skuTypeEnum;

        /**
         * 错误类型对应的编码
         */
        public final String code;
    }

    public SkuResultDTO() {
        this.errorTraceList = null;
        this.errorCodeSet = null;
    }
}
