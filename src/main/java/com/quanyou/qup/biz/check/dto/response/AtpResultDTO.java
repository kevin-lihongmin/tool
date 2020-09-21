package com.quanyou.qup.biz.check.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 *  atp 可用量查询结果
 * @author kevin
 * @date 2020/9/1 15:05
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public class AtpResultDTO extends ResultDTO {

    List<AtpInfoResultDTO> resultDTOList;

}
