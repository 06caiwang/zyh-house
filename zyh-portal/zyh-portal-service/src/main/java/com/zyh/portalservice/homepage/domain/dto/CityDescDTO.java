package com.zyh.portalservice.homepage.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangyuheng
 */
@Data
public class CityDescDTO implements Serializable {
    private Long id;
    private String name;
    private String fullName;
}
