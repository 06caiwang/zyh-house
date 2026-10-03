package com.zyh.adminservice.map.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyuheng
 * 逆地址解析结果
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class GeoResultDTO extends TencentMapBaseResponseDTO{
    /**
     * 结果信息
     */
    private AddrResultDTO result;
}
