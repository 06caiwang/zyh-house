package com.zyh.portalservice.user.domain.vo;

import com.zyh.commondomain.domain.vo.LoginUserVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyuheng
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserVO extends LoginUserVO {
    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickName;
}
