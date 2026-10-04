package com.zyh.adminservice.house.service.filter;

import com.zyh.adminapi.house.domain.dto.SearchHouseListReqDTO;
import com.zyh.adminservice.house.domain.dto.HouseDTO;

/**
 * @author zhangyuheng
 */
public interface IHouseFilter {
    /**
     * 过滤房源
     *
     * @param houseDTO
     * @param reqDTO
     * @return
     */
    Boolean filter(HouseDTO houseDTO, SearchHouseListReqDTO reqDTO);
}
