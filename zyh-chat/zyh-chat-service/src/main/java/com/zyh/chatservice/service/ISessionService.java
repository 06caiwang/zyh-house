package com.zyh.chatservice.service;

import com.zyh.chatservice.domain.dto.SessionAddReqDTO;
import com.zyh.chatservice.domain.dto.SessionGetReqDTO;
import com.zyh.chatservice.domain.vo.SessionAddResVO;
import com.zyh.chatservice.domain.vo.SessionGetResVO;

/**
 * @author zhangyuheng
 */
public interface ISessionService {
    /**
     * 新建咨询会话
     *
     * @param sessionAddReqDTO
     * @return
     */
    SessionAddResVO add(SessionAddReqDTO sessionAddReqDTO);

    /**
     * 查询俩用户的会话信息
     *
     * @param sessionGetReqDTO
     * @return
     */
    SessionGetResVO get(SessionGetReqDTO sessionGetReqDTO);
}
