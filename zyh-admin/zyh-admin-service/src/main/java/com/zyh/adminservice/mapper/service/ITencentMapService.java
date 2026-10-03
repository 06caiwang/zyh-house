package com.zyh.adminservice.mapper.service;

import com.zyh.adminservice.mapper.domain.dto.GeoResultDTO;
import com.zyh.adminservice.mapper.domain.dto.LocationDTO;
import com.zyh.adminservice.mapper.domain.dto.PoiListDTO;
import com.zyh.adminservice.mapper.domain.dto.SuggestSearchDTO;

/**
 * @author zhangyuheng
 */

public interface ITencentMapService {
    /**
     * 根据关键词搜索地点
     * @param suggestSearchDTO 搜索条件
     * @return 搜索结果
     */
    PoiListDTO searchQQMapPlaceByRegion(SuggestSearchDTO suggestSearchDTO);

    /**
     * 根据经纬度来获取区域信息
     * @param locationDTO 经纬度
     * @return 区域信息
     */
    GeoResultDTO getQQMapDistrictByLonLat(LocationDTO locationDTO);
}
