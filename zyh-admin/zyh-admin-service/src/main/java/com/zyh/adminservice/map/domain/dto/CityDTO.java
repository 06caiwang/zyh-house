package com.zyh.adminservice.map.domain.dto;

import lombok.Data;

/**
 * @author zhangyuheng
 * 城市信息
 */

@Data
public class CityDTO {
    /**
     * 城市ID
     */
    private Long id;

    /**
     * 城市名称
     */
    private String name;

    /**
     * 行政编码
     */
    private String code;

    /**
     * 城市全称
     */
    private String fullName;
}
