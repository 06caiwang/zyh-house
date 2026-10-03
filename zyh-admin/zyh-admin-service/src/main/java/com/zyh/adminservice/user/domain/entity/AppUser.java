package com.zyh.adminservice.user.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zyh.commoncore.domain.entity.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyuheng
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "app_user")
public class AppUser extends BaseDO {
    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 微信ID
     */
    private String openId;

    /**
     * 用户头像
     */
    private String avatar;
}
