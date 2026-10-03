package com.zyh.adminapi.map.domain.vo;

import lombok.Data;

/**
 * @author zhangyuheng
 * 根据地点查询返回的结果
 */
@Data
public class SearchPoiVO {
    /**
     * 行政编码
     */
    private String code;

    /**
     * 地点名称
     */
    private String title;

    /**
     * 地点地址
     */
    private String address;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;
}
