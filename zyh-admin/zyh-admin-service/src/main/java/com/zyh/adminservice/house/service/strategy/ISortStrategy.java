package com.zyh.adminservice.house.service.strategy;

import com.zyh.adminapi.house.domain.dto.SearchHouseListReqDTO;
import com.zyh.adminservice.house.domain.dto.HouseDTO;

import java.util.List;

/**
 * @author zhangyuheng
 */
public interface ISortStrategy {
    /**
     * 排序
     */
    List<HouseDTO> sort(List<HouseDTO> houseDTOList, SearchHouseListReqDTO reqDTO);
}
