package com.zyh.chatservice.service;

import com.zyh.chatservice.domain.dto.SessionStatusDetailDTO;
import com.zyh.commoncore.utils.JsonUtil;
import com.zyh.commonredis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangyuheng
 */
@Slf4j
@Component
public class ChatCacheService {

    // 会话id - 会话详细信息DTO
    private static final String CHAT_SESSION_PREFIX = "chat:session:";

    @Autowired
    private RedisService redisService;

    /**
     * 获取会话详细信息缓存
     *
     * @param sessionId
     * @return
     */
    public SessionStatusDetailDTO getSessionDTOByCache(Long sessionId) {
        SessionStatusDetailDTO sessionDTO = null;
        try {
            String key = CHAT_SESSION_PREFIX + sessionId;
            String str = redisService.getCacheObject(key, String.class);
            if (StringUtils.isBlank(str)) {
                return null;
            }
            sessionDTO = JsonUtil.string2Obj(str, SessionStatusDetailDTO.class);
        } catch (Exception e) {
            log.error("获取会话详细信息缓存时发生异常，sessionId:{}", sessionId, e);
        }

        return sessionDTO;
    }

    /**
     * 缓存会话详细信息
     *
     * @param sessionId
     * @param sessionDTO
     */
    public void cacheSessionDTO(Long sessionId, SessionStatusDetailDTO sessionDTO) {
        try {
            String key = CHAT_SESSION_PREFIX + sessionId;
            redisService.setCacheObject(key, JsonUtil.obj2String(sessionDTO));
        } catch (Exception e) {
            log.error("缓存会话详细信息时发生异常，sessionId:{}", sessionId, e);
        }
    }
}
