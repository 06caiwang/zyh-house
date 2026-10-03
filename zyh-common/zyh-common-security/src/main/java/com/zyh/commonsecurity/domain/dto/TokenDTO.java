package com.zyh.commonsecurity.domain.dto;

import com.zyh.commondomain.domain.vo.TokenVO;
import lombok.Data;

/**
 * @author zhangyuheng
 * token信息
 */
@Data
public class TokenDTO {
    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 过期时间
     */
    private Long expires;

    /**
     * 转换tokenVo
     * @return tokenVo
     */
    public TokenVO convertToVo() {
        TokenVO tokenVO = new TokenVO();
        tokenVO.setAccessToken(this.accessToken);
        tokenVO.setExpires(this.expires);
        return tokenVO;
    }
}
