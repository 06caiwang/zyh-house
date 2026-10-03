package com.zyh.commondomain.domain.vo;

import lombok.Data;

/**
 * @author zhangyuheng
 * 登录响应VO
 */
@Data
public class TokenVO {
    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 过期时间
     */
    private Long expires;
}
