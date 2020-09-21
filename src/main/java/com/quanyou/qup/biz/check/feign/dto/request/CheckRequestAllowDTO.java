package com.quanyou.qup.biz.check.feign.dto.request;

import com.quanyou.qup.biz.check.feign.dto.enums.SelectByEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 *  允销检查
 *
 * @author kevin
 * @date 2020/8/12 11:04
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
public class CheckRequestAllowDTO extends CheckRequestDTO {

    /**
     *  业务账号编码
     */
    private String accountCode;

    /**
     *  产品编码，单包件不检查允销
     */
    private List<String> skuCodeList;

    /**
     * 构造函数
     * @param selectByEnum 查询类型
     * @param accountCode 业务账号编码
     * @param skuCodeList 需要查询允销的商品列表
     */
    public CheckRequestAllowDTO(SelectByEnum selectByEnum,
                                String accountCode, List<String> skuCodeList) {
        super(null, selectByEnum);
        this.accountCode = accountCode;
        this.skuCodeList = skuCodeList;
    }
}
