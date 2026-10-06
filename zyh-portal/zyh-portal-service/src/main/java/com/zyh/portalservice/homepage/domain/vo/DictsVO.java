package com.zyh.portalservice.homepage.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangyuheng
 */
@Data
public class DictsVO implements Serializable {
    private Long id;
    private String key;
    private String name;
}
