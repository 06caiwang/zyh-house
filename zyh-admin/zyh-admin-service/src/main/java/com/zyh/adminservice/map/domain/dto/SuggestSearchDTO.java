package com.zyh.adminservice.map.domain.dto;

import lombok.Data;

/**
 * @author zhangyuheng
 * 腾讯地图城市地点查询的入参
 */
@Data
public class SuggestSearchDTO {
    /**
     * 搜索的关键字
     */
    private String keyword;

    /**
     * 城市id
     */
    private String id;

    /**
     * 页码
     */
    private Integer pageIndex;

    /**
     * 每页的数量
     */
    private Integer pageSize;
}
