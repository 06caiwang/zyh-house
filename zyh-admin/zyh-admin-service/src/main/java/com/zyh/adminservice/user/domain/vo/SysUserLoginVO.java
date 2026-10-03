package com.zyh.adminservice.user.domain.vo;

import com.zyh.commondomain.domain.vo.LoginUserVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

/**
 * @author zhangyuheng
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserLoginVO extends LoginUserVO {
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 身份
     */
    private String identity;

    /**
     * 状态
     */
    private String status;
}
