package com.zyh.adminservice.user.service;

import com.zyh.adminapi.appuser.domain.dto.AppUserDTO;
import com.zyh.adminapi.appuser.domain.dto.AppUserListReqDTO;
import com.zyh.adminapi.appuser.domain.dto.UserEditReqDTO;
import com.zyh.commoncore.domain.dto.BasePageDTO;

import java.util.List;

/**
 * @author zhangyuheng
 */
public interface IAppUserService {
    /**
     * 根据微信ID注册用户
     * @param openId 微信ID
     * @return C端用户DTO
     */
    AppUserDTO registerByOpenId(String openId);

    /**
     * 根据openId查询用户信息
     * @param openId 用户微信ID
     * @return C端用户DTO
     */
    AppUserDTO findByOpenId(String openId);

    /**
     * 根据邮箱查询用户信息
     * @param mail 手机号
     * @return C端用户DTO
     */
    AppUserDTO findByMail(String mail);

    /**
     * 根据邮箱注册用户
     * @param mail 手机号
     * @return C端用户DTO
     */
    AppUserDTO registerByMail(String mail);

    /**
     * 编辑C端用户
     * @param userEditReqDTO C端用户DTO
     */
    void edit(UserEditReqDTO userEditReqDTO);

    /**
     * 查询C端用户
     * @param appUserListReqDTO 查询C端用户DTO
     * @return C端用户分页结果DTO
     */
    BasePageDTO<AppUserDTO> getUserList(AppUserListReqDTO appUserListReqDTO);

    /**
     * 根据用户ID获取用户信息
     * @param userId 用户ID
     * @return C端用户DTO
     */
    AppUserDTO findById(Long userId);

    /**
     * 根据用户ID列表获取用户列表信息
     * @param userIds 用户ID列表
     * @return C端用户DTO列表
     */
    List<AppUserDTO> getUserList(List<Long> userIds);
}
