package com.zyh.adminservice.user.domain.dto;

import com.zyh.adminservice.user.domain.vo.SysUserLoginVO;
import com.zyh.commonsecurity.domain.dto.LoginUserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

/**
 * @author zhangyuheng
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserLoginDTO extends LoginUserDTO {
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

    /**
     * B端用户登录信息DTO转VO
     * @return B端用户登录信息VO
     */
    public SysUserLoginVO convertToVO() {
        SysUserLoginVO sysUserLoginVO = new SysUserLoginVO();
        BeanUtils.copyProperties(this, sysUserLoginVO);
        return sysUserLoginVO;
    }
}
