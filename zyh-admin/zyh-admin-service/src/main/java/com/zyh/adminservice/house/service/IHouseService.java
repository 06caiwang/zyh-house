package com.zyh.adminservice.house.service;

import com.zyh.adminservice.house.domain.dto.HouseAddOrEditReqDTO;

/**
 * @author zhangyuheng
 */
public interface IHouseService {
    /**
     * 新增或编辑房源
     *
     * @param houseAddOrEditReqDTO
     * @return
     */
    Long addOrEdit(HouseAddOrEditReqDTO houseAddOrEditReqDTO);

    /**
     * 更新房源缓存
     *
     * @param houseId
     */
    void cacheHouse(Long houseId);
}
