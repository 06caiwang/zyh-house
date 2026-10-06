package com.zyh.chatservice.service.impl;

import com.zyh.chatservice.domain.dto.MessageDTO;
import com.zyh.chatservice.domain.dto.MessageListReqDTO;
import com.zyh.chatservice.domain.vo.MessageVO;
import com.zyh.chatservice.mapper.MessageMapper;
import com.zyh.chatservice.service.ChatCacheService;
import com.zyh.chatservice.service.IMessageService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author zhangyuheng
 */
@Slf4j
@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Resource(name = "chatCacheService")
    private ChatCacheService chatCacheService;

    @Override
    public List<MessageVO> list(MessageListReqDTO messageListReqDTO) {

        // 从缓存中获取会话id下的消息全集合（倒序: 最新的消息在最前面）
        Set<MessageDTO> messageDTOSet = chatCacheService.getMessageDTOSByCache(messageListReqDTO.getSessionId());
        if (CollectionUtils.isEmpty(messageDTOSet)) {
            return Arrays.asList();
        }

        // 遍历联表，构造需要返回的结果
        List<MessageVO> resultList = new ArrayList<>();
        int curCount = messageListReqDTO.getCount();
        for (MessageDTO messageDTO : messageDTOSet) {
            // 遍历到传入的最后一条消息，需要判断下是否需要获取这个消息
            if (messageDTO.getMessageId().equalsIgnoreCase(messageListReqDTO.getLastMessageId())
                    && messageListReqDTO.getNeedCurMessage()) {
                MessageVO messageVO = new MessageVO();
                BeanUtils.copyProperties(messageDTO, messageVO);
                resultList.add(messageVO);
                curCount--;
            } else if (0 > messageDTO.getMessageId().compareTo(messageListReqDTO.getLastMessageId())) {
                // 获取历史消息
                MessageVO messageVO = new MessageVO();
                BeanUtils.copyProperties(messageDTO, messageVO);
                resultList.add(messageVO);
                curCount--;
            }

            if (curCount <= 0) {
                break;
            }

        }

        // 由于缓存中的消息是倒序的，最新的消息在最前面
        // 那么遍历的时候，往resultList add时也是最新消息在最前面
        // 需要逆置
        Collections.reverse(resultList);

        return resultList;
    }
}
