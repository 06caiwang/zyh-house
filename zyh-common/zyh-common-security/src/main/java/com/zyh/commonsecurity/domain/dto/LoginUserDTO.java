package com.zyh.commonsecurity.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangyuheng
 * 用户信息上下文
 */
@Data
public class LoginUserDTO implements Serializable {
    /**
     * 用户标识
     */
    private String token;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户来源
     */
    private String userFrom;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;
}
