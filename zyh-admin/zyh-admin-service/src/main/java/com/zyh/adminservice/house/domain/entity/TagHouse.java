package com.zyh.adminservice.house.domain.entity;

import com.zyh.commoncore.domain.entity.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyuheng
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TagHouse extends BaseDO {
    private String tagCode;
    private Long houseId;
}
