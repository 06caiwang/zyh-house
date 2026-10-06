package com.zyh.chatservice.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangyuheng
 */
@Data
public class SessionHouseReqDTO implements Serializable {
    @NotNull(message = "会话id不能为空！")
    private Long sessionId;

    @NotNull(message = "房源id不能为空！")
    private Long houseId;
}
