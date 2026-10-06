package com.zyh.portalservice.homepage.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangyuheng
 */
@Data
public class HouseDescVO implements Serializable {
    private Long houseId;
    private String headImage;
    private String title;
    private String rentType;
    private Double area;
    private String position;
    private String regionName;
    private Double price;
}
