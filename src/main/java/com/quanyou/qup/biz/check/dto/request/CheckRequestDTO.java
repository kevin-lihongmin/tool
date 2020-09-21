package com.quanyou.qup.biz.check.dto.request;

import com.quanyou.qup.biz.check.constant.CheckTypeEnum;
import com.quanyou.qup.biz.check.constant.SelectByEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *  检查参数抽象
 * @author kevin
 * @date 2020/8/12 10:20
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckRequestDTO<T extends CheckTypeEnum> {

    /**
     * id或者编码
     */
    String idOrCode;

    /**
     * 查询类型
     */
    SelectByEnum selectByEnum;

}
