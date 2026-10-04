package com.zyh.adminservice.house.service.filter;

import com.zyh.adminapi.house.domain.dto.SearchHouseListReqDTO;
import com.zyh.adminservice.house.domain.dto.HouseDTO;
import com.zyh.adminservice.house.domain.enums.HouseStatusEnum;
import org.springframework.stereotype.Component;

/**
 * @author zhangyuheng
 */
@Component
public class StatusFilter implements IHouseFilter{
    @Override
    public Boolean filter(HouseDTO houseDTO, SearchHouseListReqDTO reqDTO) {
        return houseDTO.getStatus().equalsIgnoreCase(HouseStatusEnum.UP.name());
    }
}
