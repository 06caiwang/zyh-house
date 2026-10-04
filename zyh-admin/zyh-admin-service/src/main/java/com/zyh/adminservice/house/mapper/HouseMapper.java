package com.zyh.adminservice.house.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyh.adminservice.house.domain.dto.HouseDescDTO;
import com.zyh.adminservice.house.domain.dto.HouseListReqDTO;
import com.zyh.adminservice.house.domain.entity.House;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zhangyuheng
 */
@Mapper
public interface HouseMapper extends BaseMapper<House> {
    Long selectCountWithStatus(HouseListReqDTO houseListReqDTO);

    List<HouseDescDTO> selectPageWithStatus(HouseListReqDTO houseListReqDTO);
}
