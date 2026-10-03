package com.zyh.adminservice.config.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyh.adminservice.config.domain.entity.SysArgument;
import com.zyh.adminservice.config.service.ISysArgumentService;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author zhangyuheng
 */
@Mapper
public interface SysArgumentMapper extends BaseMapper<SysArgument> {

}
