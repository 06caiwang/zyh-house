package com.zyh.chatservice.service;

import com.zyh.chatservice.domain.dto.MessageListReqDTO;
import com.zyh.chatservice.domain.vo.MessageVO;

import java.util.List;

/**
 * @author zhangyuheng
 */
public interface IMessageService {
    /**
     * 获取历史聊天记录
     *
     * @param messageListReqDTO
     * @return
     */
    List<MessageVO> list(MessageListReqDTO messageListReqDTO);
}
