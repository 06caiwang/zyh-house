package com.zyh.adminapi.map.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author zhangyuheng
 * 经纬度位置请求的DTO
 */

@Data
public class LocationReqDTO {
    /**
     * 纬度
     */
    @NotNull(message = "纬度不能为空")
    private Double lat;

    /**
     * 经度
     */
    @NotNull(message = "经度不能为空")
    private Double lng;

    /**
     * 格式化信息
     * @return 格式化后的经纬度
     */
    public String formatInfo() {
        return lat + "," + lng;
    }
}
