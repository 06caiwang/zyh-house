package com.zyh.adminservice.house.domain.entity;

import com.zyh.commoncore.domain.entity.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyuheng
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CityHouse extends BaseDO {
    private Long cityId;
    private String cityName;
    private Long houseId;
}
