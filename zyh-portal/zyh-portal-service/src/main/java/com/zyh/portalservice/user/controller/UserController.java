package com.zyh.portalservice.user.controller;

import com.zyh.adminapi.appuser.domain.dto.UserEditReqDTO;
import com.zyh.commondomain.domain.R;
import com.zyh.commondomain.domain.vo.TokenVO;
import com.zyh.portalservice.user.domain.dto.CodeLoginDTO;
import com.zyh.portalservice.user.domain.dto.WechatLoginDTO;
import com.zyh.portalservice.user.domain.vo.UserVO;
import com.zyh.portalservice.user.service.IUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangyuheng
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource(name = "userServiceImpl")
    private IUserService userService;

    /**
     * 微信登录
     * @param wechatLoginDTO 微信登录DTO
     * @return token令牌
     */
    @PostMapping("/login/wechat")
    public R<TokenVO> login(@RequestBody @Validated WechatLoginDTO wechatLoginDTO) {
        return R.ok(userService.login(wechatLoginDTO).convertToVo());
    }

    /**
     * 发送短信验证码
     * @param mail 手机号
     * @return 验证码
     */
    @GetMapping("/send_code")
    public R<String> sendCode(String mail) {
        return R.ok(userService.sendCode(mail));
    }

    /**
     * 验证码登录
     * @param codeLoginDTO 验证码登录信息
     * @return token信息VO
     */
    @PostMapping("/login/code")
    public R<TokenVO> login(@RequestBody @Validated CodeLoginDTO codeLoginDTO) {
        return R.ok(userService.login(codeLoginDTO).convertToVo());
    }

    /**
     * 修改用户信息
     * @param userEditReqDTO C端用户编辑DTO
     * @return void
     */
    @PostMapping("/edit")
    public R<Void> edit(@RequestBody @Validated UserEditReqDTO userEditReqDTO) {
        userService.edit(userEditReqDTO);
        return R.ok();
    }

    /**
     * 获取用户登录信息
     * @return 用户信息VO
     */
    @GetMapping("/login_info/get")
    public R<UserVO> getLoginUser() {
        return R.ok(userService.getLoginUser().convertToVO());
    }

    /**
     * 退出登录
     * @return void
     */
    @DeleteMapping("/logout")
    R<Void> logout() {
        userService.logout();
        return R.ok();
    }
}
