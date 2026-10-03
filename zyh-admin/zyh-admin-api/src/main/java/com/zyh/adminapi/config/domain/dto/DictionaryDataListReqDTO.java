package com.zyh.adminapi.config.domain.dto;

import com.zyh.commondomain.domain.dto.BasePageReqDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyuheng
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DictionaryDataListReqDTO extends BasePageReqDTO {
    /**
     * 字典类型业务主键
     */
    @NotBlank(message = "字典类型业务主键不能为空")
    private String typeKey;

    /**
     * 字典数据值
     */
    private String value;
}
