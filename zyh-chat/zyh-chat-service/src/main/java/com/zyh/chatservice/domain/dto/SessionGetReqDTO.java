package com.zyh.chatservice.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangyuheng
 */
@Data
public class SessionGetReqDTO implements Serializable {
    @NotNull(message = "用户1 id不能为空！")
    private Long userId1;

    @NotNull(message = "用户2 id不能为空！")
    private Long userId2;
}
