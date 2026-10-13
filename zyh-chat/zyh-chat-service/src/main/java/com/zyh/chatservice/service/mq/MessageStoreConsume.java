package com.zyh.chatservice.service.mq;

import com.zyh.chatservice.config.RabbitMqConfig;
import com.zyh.chatservice.domain.dto.MessageSendReqDTO;
import com.zyh.chatservice.service.IMessageService;
import com.zyh.commoncore.utils.JsonUtil;
import com.zyh.commondomain.exception.ServiceException;
import com.zyh.commonredis.service.RedissonLockService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangyuheng
 * 聊天持久化
 */
@Component
@Slf4j
@RabbitListener(bindings = {@QueueBinding(value = @Queue(), exchange = @Exchange(value = RabbitMqConfig.EXCHANGE_NAME, type = ExchangeTypes.FANOUT))})
public class MessageStoreConsume {
    private static final String LOCK_KEY = "chat:db:lock";

    @Autowired
    private RedissonLockService redissonLockService;

    @Autowired
    private IMessageService messageService;


    @RabbitHandler
    public void process(MessageSendReqDTO messageSendReqDTO) {

        // 获取分布式锁
        RLock rLock =  redissonLockService.acquire(LOCK_KEY, -1);
        if (null == rLock) {
            // 获取锁失败，跳过执行
            return;
        }

        try {
            // 幂等性处理：消息已存在，不处理
            if (null != messageService.get(messageSendReqDTO.getMessageId())) {
                return;
            }

            // 不存在，持久化存储
            if (!messageService.add(messageSendReqDTO)) {
                throw new ServiceException("聊天消息持久化失败！");
            }

        } catch (Exception e) {
            log.error("消息持久化异常！messageSendReqDTO:{}", JsonUtil.obj2String(messageSendReqDTO), e);
        } finally {
            // 释放锁
            if (rLock.isLocked() && rLock.isHeldByCurrentThread()) {
                redissonLockService.releaseLock(rLock);
            }
        }
    }
}
