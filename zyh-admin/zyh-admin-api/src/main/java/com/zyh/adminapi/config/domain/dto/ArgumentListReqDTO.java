package com.zyh.adminapi.config.domain.dto;

import com.zyh.commoncore.domain.dto.BasePageDTO;
import com.zyh.commondomain.domain.dto.BasePageReqDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyuheng
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ArgumentListReqDTO extends BasePageReqDTO {
    /**
     * 参数名称
     */
    private String name;

    /**
     * 参数业务主键
     */
    private String configKey;
}
