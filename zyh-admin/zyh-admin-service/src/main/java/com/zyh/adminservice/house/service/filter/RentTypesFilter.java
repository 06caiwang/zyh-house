package com.zyh.adminservice.house.service.filter;

import com.zyh.adminapi.house.domain.dto.SearchHouseListReqDTO;
import com.zyh.adminservice.house.domain.dto.HouseDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

/**
 * @author zhangyuheng
 */
@Component
public class RentTypesFilter implements IHouseFilter{
    @Override
    public Boolean filter(HouseDTO houseDTO, SearchHouseListReqDTO reqDTO) {
        return CollectionUtils.isEmpty(reqDTO.getRentTypes())
                || reqDTO.getRentTypes().contains(houseDTO.getRentType());
    }
}
