package com.zyh.portalservice.homepage.service;

import com.zyh.commondomain.domain.vo.BasePageVO;
import com.zyh.portalservice.homepage.domain.dto.HouseListReqDTO;
import com.zyh.portalservice.homepage.domain.dto.PullDataListReqDTO;
import com.zyh.portalservice.homepage.domain.vo.CityDescVO;
import com.zyh.portalservice.homepage.domain.vo.HouseDescVO;
import com.zyh.portalservice.homepage.domain.vo.PullDataListVO;

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

    /**
     * 获取下拉筛选数据列表
     *
     * @param pullDataListReqDTO
     * @return
     */
    PullDataListVO getPullData(PullDataListReqDTO pullDataListReqDTO);

    /**
     * 获取房源列表
     *
     * @param reqDTO
     * @return
     */
    BasePageVO<HouseDescVO> houseList(HouseListReqDTO reqDTO);
}
