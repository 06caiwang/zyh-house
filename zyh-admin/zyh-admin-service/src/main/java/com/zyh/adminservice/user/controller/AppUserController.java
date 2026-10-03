package com.zyh.adminservice.user.controller;

import com.zyh.adminapi.appuser.domain.dto.AppUserDTO;
import com.zyh.adminapi.appuser.domain.dto.AppUserListReqDTO;
import com.zyh.adminapi.appuser.domain.dto.UserEditReqDTO;
import com.zyh.adminapi.appuser.domain.vo.AppUserVO;
import com.zyh.adminapi.appuser.feign.AppUserFeignClient;
import com.zyh.adminservice.user.service.IAppUserService;
import com.zyh.commoncore.domain.dto.BasePageDTO;
import com.zyh.commondomain.domain.R;
import com.zyh.commondomain.domain.vo.BasePageVO;
import com.zyh.commondomain.exception.ServiceException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zhangyuheng
 */
@Slf4j
@RestController
@RequestMapping("/app_user")
public class AppUserController implements AppUserFeignClient {

    @Resource(name = "appUserServiceImpl")
    private IAppUserService appUserService;

    /**
     * 根据微信注册用户
     * @param openId 用户微信ID
     * @return C端用户VO
     */
    @Override
    public R<AppUserVO> registerByOpenId(String openId) {
        return R.ok(appUserService.registerByOpenId(openId).convertToVO());
    }

    /**
     * 根据openId查询用户信息
     * @param openId 用户微信ID
     * @return C端用户VO
     */
    @Override
    public R<AppUserVO> findByOpenId(String openId) {
        AppUserDTO appUserDTO = appUserService.findByOpenId(openId);
        if (appUserDTO == null) {
            return R.ok();
        }
        return R.ok(appUserDTO.convertToVO());
    }

    /**
     * 根据邮箱查询用户信息
     * @param mail 邮箱
     * @return C端用户VO
     */
    @Override
    public R<AppUserVO> findByMail(String mail) {
        AppUserDTO appUserDTO = appUserService.findByMail(mail);
        if (appUserDTO == null) {
            return R.ok();
        }
        return R.ok(appUserDTO.convertToVO());
    }

    /**
     * 根据邮箱注册用户
     * @param mail 邮箱
     * @return C端用户VO
     */
    @Override
    public R<AppUserVO> registerByMail(String mail) {
        AppUserDTO appUserDTO = appUserService.registerByMail(mail);
        if (appUserDTO == null) {
            throw new ServiceException("注册失败");
        }
        return R.ok(appUserDTO.convertToVO());
    }

    /**
     * 编辑C端用户
     * @param userEditReqDTO C端用户DTO
     * @return void
     */
    @Override
    public R<Void> edit(UserEditReqDTO userEditReqDTO) {
        appUserService.edit(userEditReqDTO);
        return R.ok();
    }

    /**
     * 根据用户ID获取用户信息
     * @param userId 用户ID
     * @return C端用户VO
     */
    @Override
    public R<AppUserVO> findById(Long userId) {
        AppUserDTO appUserDTO = appUserService.findById(userId);
        if (appUserDTO == null) {
            return R.ok();
        }
        return R.ok(appUserDTO.convertToVO());
    }

    /**
     * 根据用户ID列表获取用户列表信息
     * @param userIds 用户ID列表
     * @return C端用户VO列表
     */
    @Override
    public R<List<AppUserVO>> list(List<Long> userIds) {
        List<AppUserDTO> appUserDTOList = appUserService.getUserList(userIds);
        return R.ok(appUserDTOList.stream()
                .filter(Objects::nonNull)
                .map(AppUserDTO::convertToVO)
                .collect(Collectors.toList())
        );
    }

    /**
     * 查询C端用户
     * @param appUserListReqDTO 查询C端用户DTO
     * @return C端用户分页结果
     */
    @PostMapping("/list/search")
    public R<BasePageVO<AppUserVO>> list(@RequestBody AppUserListReqDTO appUserListReqDTO) {
        BasePageDTO<AppUserDTO> appUserDTOList = appUserService.getUserList(appUserListReqDTO);
        BasePageVO<AppUserVO> result = new BasePageVO<>();
        BeanUtils.copyProperties(appUserDTOList, result);
        return R.ok(result);
    }
}
