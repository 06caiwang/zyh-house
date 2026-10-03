package com.zyh.portalservice.user.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyuheng
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CodeLoginDTO extends LoginDTO{
    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    private String mail;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String code;
}
