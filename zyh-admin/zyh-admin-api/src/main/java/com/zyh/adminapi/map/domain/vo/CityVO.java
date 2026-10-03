package com.zyh.adminapi.map.domain.vo;

import lombok.Data;

/**
 * @author zhangyuheng
 * 城市信息
 */

@Data
public class CityVO {
    /**
     * 城市ID
     */
    private Long id;

    /**
     * 行政编码
     */
    private String code;

    /**
     * 城市名称
     */
    private String name;

    /**
     * 城市全称
     */
    private String fullName;
}
