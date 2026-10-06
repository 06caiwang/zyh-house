package com.zyh.chatservice.service.mq;

import com.zyh.chatservice.config.RabbitMqConfig;
import com.zyh.chatservice.domain.dto.MessageSendReqDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author zhangyuheng
 */
@Slf4j
@Component
public class MessageProduce {
    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(MessageSendReqDTO messageSendReqDTO) {
        try {
            rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME, "", messageSendReqDTO);
        } catch (Exception e) {
            log.error("咨询聊天消息发送异常！", e);
        }
    }
}
