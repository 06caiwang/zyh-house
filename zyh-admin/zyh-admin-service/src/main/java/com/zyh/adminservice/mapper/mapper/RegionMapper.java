package com.zyh.adminservice.mapper.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyh.adminservice.mapper.domain.entity.SysRegion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zhangyuheng
 */
@Mapper
public interface RegionMapper extends BaseMapper<SysRegion> {
    /**
     * 获取全量区域信息
     * @return 区域列表
     */
    List<SysRegion> selectAllRegion();
}
