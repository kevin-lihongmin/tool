package com.biz.check.feign.dto.request;

import com.biz.check.feign.dto.enums.CheckTypeEnum;
import com.biz.check.feign.dto.enums.SelectByEnum;
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
