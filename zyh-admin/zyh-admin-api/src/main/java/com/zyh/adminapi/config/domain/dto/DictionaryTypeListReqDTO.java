package com.zyh.adminapi.config.domain.dto;

import com.zyh.commoncore.domain.dto.BasePageDTO;
import com.zyh.commondomain.domain.dto.BasePageReqDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyuheng
 * 字典类型列表DTO
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class DictionaryTypeListReqDTO extends BasePageReqDTO {
    /**
     * 字典类型值
     */
    private String value;

    /**
     * 字典类型键
     */
    private String typeKey;
}
