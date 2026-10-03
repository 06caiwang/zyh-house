package com.zyh.adminservice.map.controller;

import com.zyh.adminapi.map.domain.dto.LocationReqDTO;
import com.zyh.adminapi.map.domain.dto.PlaceSearchReqDTO;
import com.zyh.adminapi.map.domain.dto.SysRegionDTO;
import com.zyh.adminapi.map.domain.vo.CityVO;
import com.zyh.adminapi.map.domain.vo.RegionVO;
import com.zyh.adminapi.map.domain.vo.SearchPoiVO;
import com.zyh.adminapi.map.feigen.MapFeignClient;
import com.zyh.adminservice.map.domain.dto.CityDTO;
import com.zyh.adminservice.map.domain.dto.SearchPoiDTO;
import com.zyh.adminservice.map.service.IMapService;
import com.zyh.commoncore.utils.BeanCopyUtil;
import com.zyh.commoncore.utils.JsonUtil;
import com.zyh.commondomain.domain.R;
import com.zyh.commondomain.domain.dto.BasePageDTO;
import com.zyh.commondomain.domain.vo.BasePageVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyuheng
 */

@RestController
@Slf4j
public class MapController implements MapFeignClient {

    @Resource(name = "mapServiceImpl")
    private IMapService mapService;

    /**
     * 获取区域信息
     * @return 区域列表
     */
    @Override
    public R<List<RegionVO>> getRegionList() {
        // 1. 调用service
        List<SysRegionDTO> list = mapService.getRegionList();

        // 2. 转换成 VO
        List<RegionVO> response = BeanCopyUtil.copyListProperties(list, RegionVO::new);

        // 3. 返回结果
        return R.ok(response);
    }

    /**
     * 获取城市的拼音排序列表
     * @return 排序好的城市列表
     */
    @Override
    public R<Map<String, List<RegionVO>>> getCityListByPingYin() {
        // 1. 打印日志
        log.info("getCityListByPingYin: NoArgs");

        // 2. 调用service
        Map<String, List<SysRegionDTO>> map = mapService.getCityListByPingYin();

        // 3. 转换VO
        Map<String, List<RegionVO>> result = new LinkedHashMap<>();
        for (Map.Entry<String, List<SysRegionDTO>> region : map.entrySet()) {
            result.put(
                    region.getKey(),
                    BeanCopyUtil.copyListProperties(region.getValue(), RegionVO::new)
            );
        }

        //4. 返回结果
        return R.ok(result);
    }

    /**
     * 根据父级区域ID获取子集区域列表
     * @param parentId 父级区域ID
     * @return 子集区域列表
     */
    @Override
    public R<List<RegionVO>> getChildrenCityList(Long parentId) {
        // 1. 打印日志
        log.info("getChildrenCityList: {parentId(Long)}");

        // 2. 调用service
        List<SysRegionDTO> list = mapService.getChildrenCityList(parentId);

        // 3. 转换成VO
        List<RegionVO> response = BeanCopyUtil.copyListProperties(list, RegionVO::new);

        // 4. 返回结果
        return R.ok(response);
    }

    /**
     * 获取热门城市列表
     * @return 城市列表
     */
    @Override
    public R<List<RegionVO>> getHotCityList() {
        // 1. 打印日志
        log.info("getHotCityList NoArgs");

        // 2. 调用service
        List<SysRegionDTO> list = mapService.getHotCityList();

        // 3. 转换VO
        List<RegionVO> response = BeanCopyUtil.copyListProperties(list, RegionVO::new);

        // 4. 返回结果
        return R.ok(response);
    }


    /**
     * 根据地点搜索
     * @param placeSearchReqDTO 搜索条件
     * @return 搜索结果
     */
    @Override
    public R<BasePageVO<SearchPoiVO>> searchSuggestOnMap(PlaceSearchReqDTO placeSearchReqDTO) {
        // 1. 打印日志
        log.info("searchSuggestOnMap: {}", JsonUtil.obj2String(placeSearchReqDTO));

        // 2. service
        BasePageDTO<SearchPoiDTO> list =  mapService.searchSuggestOnMap(placeSearchReqDTO);

        // 3. 转换VO
        BasePageVO<SearchPoiVO> response = new BasePageVO<>();
        BeanUtils.copyProperties(list, response);

        // 4. 返回结果
        return R.ok(response);
    }

    /**
     * 根据经纬度来定位城市
     * @param locationReqDTO 经纬度信息
     * @return 城市信息
     */
    @Override
    public R<CityVO> locateCityByLocation(LocationReqDTO locationReqDTO) {
        // 1. 打印日志
        log.info("locateCityByLocation: {}", JsonUtil.obj2String(locationReqDTO));

        // 2. service
        CityDTO cityDTO =  mapService.locateCityByLocation(locationReqDTO);

        // 3. 转换VO
        CityVO response = new CityVO();
        BeanUtils.copyProperties(cityDTO, response);

        // 4. 返回结果
        return R.ok(response);
    }
}
