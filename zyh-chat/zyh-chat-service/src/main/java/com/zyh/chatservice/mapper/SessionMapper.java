package com.zyh.chatservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyh.chatservice.domain.entity.Session;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhangyuheng
 */
@Mapper
public interface SessionMapper extends BaseMapper<Session> {
}
