package com.zyh.adminservice.user.service.impl;

import com.zyh.adminapi.appuser.domain.dto.AppUserDTO;
import com.zyh.adminapi.appuser.domain.dto.AppUserListReqDTO;
import com.zyh.adminapi.appuser.domain.dto.UserEditReqDTO;
import com.zyh.adminservice.user.config.RabbitConfig;
import com.zyh.adminservice.user.domain.entity.AppUser;
import com.zyh.adminservice.user.mapper.AppUserMapper;
import com.zyh.adminservice.user.service.IAppUserService;
import com.zyh.commoncore.domain.dto.BasePageDTO;
import com.zyh.commoncore.utils.AESUtil;
import com.zyh.commondomain.domain.ResultCode;
import com.zyh.commondomain.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangyuheng
 */
@Slf4j
@Service
public class AppUserServiceImpl implements IAppUserService {

    @Autowired
    private AppUserMapper appUserMapper;

    @Value("${appuser.info.defaultAvatar}")
    private String defaultAvatar;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 根据微信ID注册用户
     * @param openId 微信ID
     * @return C端用户DTO
     */
    @Override
    public AppUserDTO registerByOpenId(String openId) {
        if (StringUtils.isEmpty(openId)) {
            throw new ServiceException("微信ID不能为空", ResultCode.INVALID_PARA.getCode());
        }
        AppUser appUser = new AppUser();
        appUser.setOpenId(openId);
        appUser.setNickName("用户"+ (int) (Math.random() * 9000) + 1000);
        appUser.setAvatar(defaultAvatar);
        appUserMapper.insert(appUser);
        AppUserDTO appUserDTO = new AppUserDTO();
        BeanUtils.copyProperties(appUser, appUserDTO);
        appUserDTO.setUserId(appUser.getId());
        
        return appUserDTO;
    }

    /**
     * 根据openId查询用户信息
     * @param openId 用户微信ID
     * @return C端用户DTO
     */
    @Override
    public AppUserDTO findByOpenId(String openId) {
        if (StringUtils.isEmpty(openId)) {
            return null;
        }

        // 1 查询appUser实体类
        AppUser appUser = appUserMapper.selectByOpenId(openId);

        // 2 对查出来的结果进行判断
        if (appUser == null) {
            return null;
        }
        
        AppUserDTO appUserDTO = new AppUserDTO();
        BeanUtils.copyProperties(appUser, appUserDTO);
        
        // 3 处理邮箱号
        appUserDTO.setPhoneNumber(AESUtil.decryptHex(appUser.getPhoneNumber()));
        return appUserDTO;
    }

    /**
     * 根据邮箱号查询用户信息
     * @param mail 邮箱号
     * @return C端用户DTO
     */
    @Override
    public AppUserDTO findByMail(String mail) {
        // 1 对入参判空
        if (StringUtils.isEmpty(mail)) {
            return null;
        }
        
        // 2 执行查询语句
        AppUser appUser = appUserMapper.selectByMail(mail);
        if (appUser == null) {
            return null;
        }
        
        // 3 对查出来的结果进行类型转换
        AppUserDTO appUserDTO = new AppUserDTO();
        BeanUtils.copyProperties(appUser, appUserDTO);
        appUserDTO.setUserId(appUser.getId());
        return appUserDTO;
    }

    /**
     * 根据邮箱注册用户
     * @param mail 邮箱
     * @return C端用户DTO
     */
    @Override
    public AppUserDTO registerByMail(String mail) {
        // 1 对邮箱进行判断
        if (StringUtils.isEmpty(mail)) {
            throw new ServiceException("要注册邮箱是空的", ResultCode.INVALID_PARA.getCode());
        }

        // 2. 查询是否存存在该邮箱
        AppUser cache = appUserMapper.selectByMail(mail);
        if (cache != null) {
            throw new ServiceException("该邮箱已注册，请勿重复注册", ResultCode.MAIL_EXISTS.getCode());
        }

        // 3. 生成用户对象
        AppUser appUser = new AppUser();
        appUser.setNickName("用户"+ (int) (Math.random() * 9000) + 1000);
        appUser.setMail(mail);
        appUser.setAvatar(defaultAvatar);
        appUserMapper.insert(appUser);
        AppUserDTO appUserDTO = new AppUserDTO();
        BeanUtils.copyProperties(appUser, appUserDTO);
        appUserDTO.setUserId(appUser.getId());
        return appUserDTO;
    }

    @Override
    public void edit(UserEditReqDTO userEditReqDTO) {
        // 1 根据ID查询要编辑的用户
        AppUser appUser = appUserMapper.selectById(userEditReqDTO.getUserId());
        if (appUser == null) {
            throw new ServiceException("用户不存在", ResultCode.INVALID_PARA.getCode());
        }
        // 2 查到用户，进行编辑操作
        appUser.setNickName(userEditReqDTO.getNickName());
        appUser.setAvatar(userEditReqDTO.getAvtar());
        appUserMapper.updateById(appUser);
        // 3 发送广播消息
        AppUserDTO appUserDTO = new AppUserDTO();
        BeanUtils.copyProperties(appUser, appUserDTO);
        appUserDTO.setUserId(appUser.getId());
        try {
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, "", appUserDTO);
        } catch (Exception exception) {
            log.error("编辑用户发送消息失败", exception);
        }
    }

    /**
     * 查询C端用户
     * @param appUserListReqDTO 查询C端用户DTO
     * @return C端用户分页结果DTO
     */
    @Override
    public BasePageDTO<AppUserDTO> getUserList(AppUserListReqDTO appUserListReqDTO) {
        // 1 先把手机号转变过来
        appUserListReqDTO.setPhoneNumber(AESUtil.encryptHex(appUserListReqDTO.getPhoneNumber()));
        BasePageDTO<AppUserDTO> result = new BasePageDTO<>();

        // 2 查询总数
        Long totals = appUserMapper.selectCount(appUserListReqDTO);
        if (totals == 0) {
            result.setTotals(0);
            result.setTotalPages(0);
            result.setList(new ArrayList<>());
            return result;
        }

        // 3 分页查询
        List<AppUser> appUserList = appUserMapper.selectPage(appUserListReqDTO);
        result.setTotals(totals.intValue());
        result.setTotalPages(
                BasePageDTO.calculateTotalPages(totals, appUserListReqDTO.getPageSize())
        );
        // 4 超页
        if (CollectionUtils.isEmpty(appUserList)) {
            result.setList(new ArrayList<>());
            return result;
        }
        // 5 对象列表结果转换
        result.setList(
                appUserList.stream()
                        .map(appUser -> {
                            AppUserDTO appUserDTO = new AppUserDTO();
                            BeanUtils.copyProperties(appUser, appUserDTO);
                            appUserDTO.setUserId(appUser.getId());
                            appUserDTO.setPhoneNumber(AESUtil.decryptHex(appUser.getPhoneNumber()));
                            return appUserDTO;
                        }).collect(Collectors.toList())
        );
        return result;
    }

    /**
     * 根据用户ID获取用户信息
     * @param userId 用户ID
     * @return C端用户DTO
     */
    @Override
    public AppUserDTO findById(Long userId) {
        // 1 对userId进行判空操作
        if (userId == null) {
            return null;
        }
        // 2 查询appUser对象
        AppUser appUser = appUserMapper.selectById(userId);
        if (appUser == null) {
            return null;
        }
        // 3 对象转换
        AppUserDTO appUserDTO = new AppUserDTO();
        BeanUtils.copyProperties(appUser, appUserDTO);
        appUserDTO.setPhoneNumber(AESUtil.decryptHex(appUser.getPhoneNumber()));
        appUserDTO.setUserId(appUser.getId());
        return appUserDTO;
    }

    /**
     * 根据用户ID列表获取用户列表信息
     * @param userIds 用户ID列表
     * @return C端用户DTO列表
     */
    @Override
    public List<AppUserDTO> getUserList(List<Long> userIds) {
        // 1 对入参进行判空
        if (CollectionUtils.isEmpty(userIds)) {
            return Arrays.asList();
        }
        // 2 查询appUser列表
        List<AppUser> appUserList = appUserMapper.selectBatchIds(userIds);

        // 3 对象转换
        return appUserList.stream()
                .map(appUser -> {
                    AppUserDTO appUserDTO = new AppUserDTO();
                    BeanUtils.copyProperties(appUser, appUserDTO);
                    appUserDTO.setPhoneNumber(AESUtil.decryptHex(appUser.getPhoneNumber()));
                    appUserDTO.setUserId(appUser.getId());
                    return appUserDTO;
                }).collect(Collectors.toList());
    }
}
