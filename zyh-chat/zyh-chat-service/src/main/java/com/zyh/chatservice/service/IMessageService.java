package com.zyh.chatservice.service;

import com.zyh.chatservice.domain.dto.MessageDTO;
import com.zyh.chatservice.domain.dto.MessageListReqDTO;
import com.zyh.chatservice.domain.dto.MessageSendReqDTO;
import com.zyh.chatservice.domain.vo.MessageVO;

import java.util.List;

/**
 * @author zhangyuheng
 */
public interface IMessageService {
    /**
     * 根据消息id获取消息信息
     *
     * @param messageId
     * @return
     */
    MessageDTO get(Long messageId);

    /**
     * 新增一条消息
     *
     * @param reqDTO
     * @return
     */
    boolean add(MessageSendReqDTO reqDTO);

    /**
     * 获取历史聊天记录
     *
     * @param messageListReqDTO
     * @return
     */
    List<MessageVO> list(MessageListReqDTO messageListReqDTO);
}
