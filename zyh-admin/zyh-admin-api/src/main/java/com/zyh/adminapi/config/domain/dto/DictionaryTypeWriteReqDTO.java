package com.zyh.adminapi.config.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author zhangyuheng
 */

@Data
public class DictionaryTypeWriteReqDTO {
    /**
     * 字典类型值
     */
    @NotBlank(message = "字典类型值不能为空")
    private String value;

    /**
     * 字典类型键
     */
    @NotBlank(message = "字典类型键不能为空")
    private String typeKey;

    /**
     * 备注
     */
    private String remark;
}
