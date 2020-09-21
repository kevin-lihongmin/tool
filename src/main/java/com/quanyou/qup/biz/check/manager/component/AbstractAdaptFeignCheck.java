package com.quanyou.qup.biz.check.manager.component;

import com.quanyou.qup.biz.check.constant.SelectByEnum;
import com.quanyou.qup.biz.check.dto.request.CheckRequestDTO;
import com.quanyou.qup.biz.check.dto.response.CheckResponseDTO;

/**
 *  检查服务， 适配
 * @author kevin
 * @date 2020/8/13 9:16
 * @since 1.0.0
 */
public abstract class AbstractAdaptFeignCheck extends AbstractCheckService {

    /**
     *  适配检查服务
     * @param idOrCode 主键或编码
     * @param selectByEnum 查询类型枚举
     * @return 是否检查通过
     */
    public Boolean adaptCheckPass(String idOrCode, SelectByEnum selectByEnum) {
        return checkPass(new CheckRequestDTO(idOrCode, selectByEnum));
    }

    /**
     *  适配检查服务, 需要返回结果
     * @param idOrCode 主键或编码
     * @param selectByEnum 查询类型枚举
     * @return 是否检查通过
     */
    public CheckResponseDTO adaptCheck(String idOrCode, SelectByEnum selectByEnum) {
        return callService(new CheckRequestDTO(idOrCode, selectByEnum));
    }

}
