package com.zyh.portalservice.homepage.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangyuheng
 */
@Data
public class CityDescVO implements Serializable {
    private Long id;
    private String name;
    private String fullName;
}
