package com.zyh.chatservice.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangyuheng
 */
@Data
public class MessageVisitedReqDTO implements Serializable {
    @NotNull(message = "会话id不能为空！")
    private Long sessionId;
}
