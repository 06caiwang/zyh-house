package com.zyh.adminservice.user.controller;

import com.zyh.adminservice.user.domain.dto.PasswordLoginDTO;
import com.zyh.adminservice.user.domain.dto.SysUserDTO;
import com.zyh.adminservice.user.domain.dto.SysUserListReqDTO;
import com.zyh.adminservice.user.domain.vo.SysUserLoginVO;
import com.zyh.adminservice.user.domain.vo.SysUserVO;
import com.zyh.adminservice.user.service.ISysUserService;
import com.zyh.commoncore.utils.JsonUtil;
import com.zyh.commondomain.domain.R;
import com.zyh.commondomain.domain.vo.TokenVO;
import com.zyh.commonsecurity.domain.dto.TokenDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangyuheng
 */
@Slf4j
@RestController
@RequestMapping("/sys_user")
public class SysUserController {

    @Resource(name = "sysUserServiceImpl")
    private ISysUserService sysUserService;

    /**
     * B端用户登录
     *
     * @param passwordLoginDTO B端用户登录DTO
     * @return token信息
     */
    @PostMapping("/login/password")
    public R<TokenVO> login(@Validated @RequestBody PasswordLoginDTO passwordLoginDTO) {
        // 打印日志
        log.info("login PasswordLoginDTO: {}", JsonUtil.obj2String(passwordLoginDTO));
        // 调用service
        TokenDTO tokenDTO = sysUserService.login(passwordLoginDTO);

        // 返回结果
        return R.ok(tokenDTO.convertToVo());
    }

    /**
     * 新增或编辑用户
     * @param sysUserDTO B端用户信息
     * @return  用户ID
     */
    @PostMapping("/add_edit")
    public R<Long> addOrEditUser(@RequestBody SysUserDTO sysUserDTO) {
        // 打印日志
        log.info("addOrEditUser SysUserDTO: {}", JsonUtil.obj2String(sysUserDTO));

        // 调用service,返回结果
        return R.ok(sysUserService.addOrEdit(sysUserDTO));
    }

    /**
     * 查询B端用户
     * @param sysUserListReqDTO 用户查询DTO
     * @return B用户列表
     */
    @PostMapping("/list")
    public R<List<SysUserVO>> getUserList(@RequestBody SysUserListReqDTO sysUserListReqDTO) {
        List<SysUserDTO> sysUserDTOS = sysUserService.getUserList(sysUserListReqDTO);
        return R.ok(sysUserDTOS.stream()
                .map(SysUserDTO::convertToVO)
                .collect(Collectors.toList())
        );
    }

    /**
     * 获取B端登录用户信息
     * @return B端用户信息VO
     */
    @GetMapping("/login_info/get")
    public R<SysUserLoginVO> getLoginUser() {
        return R.ok(sysUserService.getLoginUser().convertToVO());
    }

}
