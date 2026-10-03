package com.zyh.adminapi.appuser.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangyuheng
 */
@Data
public class UserEditReqDTO implements Serializable {
    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称不能为空")
    private String nickName;

    /**
     * 用户头像
     */
    @NotBlank(message = "用户头像不能为空")
    private String avtar;
}
