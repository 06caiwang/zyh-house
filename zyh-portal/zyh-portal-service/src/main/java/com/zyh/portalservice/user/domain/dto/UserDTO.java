package com.zyh.portalservice.user.domain.dto;

import com.zyh.commonsecurity.domain.dto.LoginUserDTO;
import com.zyh.portalservice.user.domain.vo.UserVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

/**
 * @author zhangyuheng
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends LoginUserDTO {
    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 对象转换
     * @return 对象
     */
    public UserVO convertToVO() {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(this, userVO);
        userVO.setNickName(this.getUserName());
        return userVO;
    }
}
