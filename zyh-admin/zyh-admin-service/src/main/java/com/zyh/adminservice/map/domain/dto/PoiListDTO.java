package com.zyh.adminservice.map.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author zhangyuheng
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class PoiListDTO extends TencentMapBaseResponseDTO {
    /**
     * 本次搜索的结果数
     */
    private Integer count;

    /**
     * 查出来的poi列表
     */
    private List<PoiDTO> data;
}
