package com.zyh.adminservice.house.service.filter;

import com.zyh.adminapi.house.domain.dto.SearchHouseListReqDTO;
import com.zyh.adminservice.house.domain.dto.HouseDTO;
import org.springframework.stereotype.Component;

/**
 * @author zhangyuheng
 */
@Component
public class RegionFilter implements IHouseFilter{
    @Override
    public Boolean filter(HouseDTO houseDTO, SearchHouseListReqDTO reqDTO) {
        // 不设置区域筛选条件
        // 传递的区域筛选条件与房源的所在区一致
        return null == reqDTO.getRegionId() || houseDTO.getRegionId().equals(reqDTO.getRegionId());
    }
}
