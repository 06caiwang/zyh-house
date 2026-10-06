package com.zyh.chatservice.domain.vo;

import com.zyh.adminapi.appuser.domain.vo.AppUserVO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangyuheng
 */
@Data
public class SessionAddResVO implements Serializable {
    private Long sessionId;

    private AppUserVO loginUser;

    private AppUserVO otherUser;

}
