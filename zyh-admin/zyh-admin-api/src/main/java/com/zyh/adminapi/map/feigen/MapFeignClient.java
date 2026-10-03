package com.zyh.adminapi.map.feigen;

import com.zyh.adminapi.map.domain.dto.LocationReqDTO;
import com.zyh.adminapi.map.domain.dto.PlaceSearchReqDTO;
import com.zyh.adminapi.map.domain.vo.CityVO;
import com.zyh.adminapi.map.domain.vo.RegionVO;
import com.zyh.adminapi.map.domain.vo.SearchPoiVO;
import com.zyh.commondomain.domain.R;
import com.zyh.commondomain.domain.vo.BasePageVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyuheng
 * 地图服务远程调用
 */
@FeignClient(contextId = "mapFeignClient", value = "zyh-admin")
public interface MapFeignClient {

    /**
     * 获取所有区域信息
     * @return 区域列表
     */
    @GetMapping("/map/city_list")
    public R<List<RegionVO>> getRegionList();

    /**
     * 获取城市的拼音排序列表
     * @return 排序好的城市列表
     */
    @GetMapping("map/city_pinyin_list")
    public R<Map<String, List<RegionVO>>> getCityListByPingYin();

    /**
     * 获取城市的区信息
     * @param parentId 城市id
     * @return 城市市区信息
     */
    @GetMapping("map/region_children_list")
    public R<List<RegionVO>> getChildrenCityList(@RequestParam Long parentId);

    /**
     * 获取热门城市列表
     * @return 城市列表
     */
    @GetMapping("/map/city_hot_list")
    R<List<RegionVO>> getHotCityList();

    /**
     * 根据地点搜索
     * @param placeSearchReqDTO 搜索条件
     * @return 搜索结果
     */
    @PostMapping("/map/search")
    R<BasePageVO<SearchPoiVO>> searchSuggestOnMap(@RequestBody PlaceSearchReqDTO placeSearchReqDTO);

    /**
     * 根据经纬度来定位城市
     * @param locationReqDTO 经纬度信息
     * @return 城市信息
     */
    @PostMapping("/map/locate_city_by_location")
    R<CityVO> locateCityByLocation(@RequestBody LocationReqDTO locationReqDTO);
}
