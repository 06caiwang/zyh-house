package com.zyh.chatservice.domain.vo;

import com.zyh.adminapi.appuser.domain.vo.AppUserVO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangyuheng
 */
@Data
public class SessionGetResVO implements Serializable {
    /**
     * 会话Id
     */
    private Long sessionId;
    /**
     * 最后一条消息信息
     */
    private MessageVO lastMessageVO;
    /**
     * 最后会话时间
     */
    private Long lastSessionTime;
    /**
     * 消息未浏览数
     */
    private Integer notVisitedCount;
    /**
     * 对方信息
     */
    private AppUserVO otherUser;
}
