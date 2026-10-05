package com.zyh.portalservice.homepage.service.impl;

import com.zyh.adminapi.map.domain.dto.LocationReqDTO;
import com.zyh.adminapi.map.domain.vo.CityVO;
import com.zyh.adminapi.map.feigen.MapFeignClient;
import com.zyh.commondomain.domain.R;
import com.zyh.commondomain.domain.ResultCode;
import com.zyh.commondomain.exception.ServiceException;
import com.zyh.portalservice.homepage.domain.vo.CityDescVO;
import com.zyh.portalservice.homepage.service.IHomePageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangyuheng
 */
@Slf4j
@Service
public class HomePageServiceImpl implements IHomePageService {

    @Autowired
    private MapFeignClient mapFeignClient;

    @Override
    public CityDescVO getCityDesc(Double lat, Double lng) {
        // 校验参数
        if(null == lat || null == lng) {
            throw new ServiceException("城市经纬度不能为空！", ResultCode.INVALID_PARA.getCode());
        }

        // 发起远程调用：map服务
        LocationReqDTO locationReqDTO = new LocationReqDTO();
        locationReqDTO.setLat(lat);
        locationReqDTO.setLng(lng);
        R<CityVO> result = mapFeignClient.locateCityByLocation(locationReqDTO);
        if (null == result
                || result.getCode() != ResultCode.SUCCESS.getCode()
                || null == result.getData()) {
            throw new ServiceException("根据定位获取城市信息失败！");
        }

        // 构造返回
        CityDescVO cityDescVO = new CityDescVO();
        BeanUtils.copyProperties(result.getData(), cityDescVO);
        return cityDescVO;
    }
}
