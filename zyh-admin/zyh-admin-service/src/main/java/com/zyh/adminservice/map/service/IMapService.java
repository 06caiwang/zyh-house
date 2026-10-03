package com.zyh.adminservice.map.service;

import com.zyh.adminapi.map.domain.dto.LocationReqDTO;
import com.zyh.adminapi.map.domain.dto.PlaceSearchReqDTO;
import com.zyh.adminapi.map.domain.dto.SysRegionDTO;
import com.zyh.adminservice.map.domain.dto.CityDTO;
import com.zyh.adminservice.map.domain.dto.SearchPoiDTO;
import com.zyh.commondomain.domain.dto.BasePageDTO;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyuheng
 */
public interface IMapService {

    /**
     * 获取区域信息
     * @return 区域列表
     */
    List<SysRegionDTO> getRegionList();

    /**
     * 获取城市的拼音排序列表
     * @return 排序好的城市列表
     */
    Map<String, List<SysRegionDTO>> getCityListByPingYin();

    /**
     * 获取城市的区信息
     * @param parentId 城市id
     * @return 城市市区信息
     */
    List<SysRegionDTO> getChildrenCityList(Long parentId);

    /**
     * 获取热门城市列表
     * @return 城市列表
     */
    List<SysRegionDTO> getHotCityList();

    /**
     * 根据地点搜索
     * @param placeSearchReqDTO 搜索条件
     * @return 搜索结果
     */
    BasePageDTO<SearchPoiDTO> searchSuggestOnMap(PlaceSearchReqDTO placeSearchReqDTO);

    /**
     * 根据经纬度来定位城市
     * @param locationReqDTO 经纬度信息
     * @return 城市信息
     */
    CityDTO locateCityByLocation(LocationReqDTO locationReqDTO);
}
