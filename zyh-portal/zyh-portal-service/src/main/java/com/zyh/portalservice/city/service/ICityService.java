package com.zyh.portalservice.city.service;

import com.zyh.portalservice.city.domain.vo.CityPageVO;

/**
 * @author zhangyuheng
 */
public interface ICityService {
    /**
     * 获取热门城市与全城市列表
     *
     * @return
     */
    CityPageVO getCityPage();
}
