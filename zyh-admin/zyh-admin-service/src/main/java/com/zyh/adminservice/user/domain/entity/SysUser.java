package com.zyh.adminservice.user.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zyh.commoncore.domain.entity.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyuheng
 */
@Data
@TableName(value = "sys_user")
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseDO {
    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 密码
     */
    private String password;

    /**
     * 身份
     */
    private String identity;

    /**
     * 状态
     */
    private String status;

    /**
     * 备注
     */
    private String remark;
}
