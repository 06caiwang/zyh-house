package com.zyh.commondomain.domain.vo;

import lombok.Data;

/**
 * @author zhangyuheng
 * 用户登录信息
 */
@Data
public class LoginUserVO {
    /**
     * 用户标识
     */
    private String token;

    /**
     * 用户ID
     */
    private Long userId;

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
