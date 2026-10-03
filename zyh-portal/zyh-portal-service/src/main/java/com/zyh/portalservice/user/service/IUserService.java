package com.zyh.portalservice.user.service;

import com.zyh.adminapi.appuser.domain.dto.UserEditReqDTO;
import com.zyh.commonsecurity.domain.dto.TokenDTO;
import com.zyh.portalservice.user.domain.dto.LoginDTO;
import com.zyh.portalservice.user.domain.dto.UserDTO;

/**
 * @author zhangyuheng
 */
public interface IUserService {
    TokenDTO login(LoginDTO loginDTO);

    String sendCode(String mail);

    void edit(UserEditReqDTO userEditReqDTO);

    UserDTO getLoginUser();

    void logout();
}
