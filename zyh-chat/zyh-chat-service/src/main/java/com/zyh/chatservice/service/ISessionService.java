package com.zyh.chatservice.service;

import com.zyh.chatservice.domain.dto.SessionAddReqDTO;
import com.zyh.chatservice.domain.vo.SessionAddResVO;

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
}
