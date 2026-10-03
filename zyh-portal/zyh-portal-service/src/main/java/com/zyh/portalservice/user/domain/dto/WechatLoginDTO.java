package com.zyh.portalservice.user.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyuheng
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WechatLoginDTO extends LoginDTO {
    /**
     * 微信openId
     */
    @NotBlank(message = "微信openId不能为空")
    private String openId;
}
