package com.zyh.portalservice.house.service;

import com.zyh.portalservice.house.domain.vo.HouseDataVO;

/**
 * @author zhangyuheng
 */
public interface IHouseService {
    /**
     * 查询房源详细信息
     *
     * @param houseId
     * @return
     */
    HouseDataVO houseDetail(Long houseId);
}
