package com.biz.check.feign.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *  atp 可用量查询结果
 * @author kevin
 * @date 2020/9/1 15:05
 * @since 1.0.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AtpResultDTO extends ResultDTO {

    private List<AtpInfoResultDTO> resultDTOList;



}
