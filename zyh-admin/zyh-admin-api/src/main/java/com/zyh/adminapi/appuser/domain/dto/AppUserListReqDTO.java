package com.zyh.adminapi.appuser.domain.dto;

import com.zyh.commondomain.domain.dto.BasePageReqDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author zhangyuheng
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AppUserListReqDTO extends BasePageReqDTO implements Serializable {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 微信openId
     */
    private String openId;
}
