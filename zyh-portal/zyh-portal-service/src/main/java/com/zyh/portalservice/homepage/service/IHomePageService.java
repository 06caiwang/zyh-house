package com.zyh.portalservice.homepage.service;

import com.zyh.portalservice.homepage.domain.vo.CityDescVO;

/**
 * @author zhangyuheng
 */
public interface IHomePageService {
    /**
     * 根据经纬度获取城市信息
     *
     * @param lat
     * @param lng
     * @return
     */
    CityDescVO getCityDesc(Double lat, Double lng);
}
