package com.zyh.portalservice.homepage.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangyuheng
 */
@Data
public class DictDataDTO implements Serializable {
    /**
     * 字典数据ID
     */
    private Long id;

    /**
     * 字典类型键
     */
    private String typeKey;

    /**
     * 字典数据键
     */
    private String dataKey;

    /**
     * 字典数据值
     */
    private String value;
}
