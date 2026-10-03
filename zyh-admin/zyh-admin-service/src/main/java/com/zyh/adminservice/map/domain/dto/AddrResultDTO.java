package com.zyh.adminservice.map.domain.dto;

import lombok.Data;

/**
 * @author zhangyuheng
 * 逆地址解析结果
 */

@Data
public class AddrResultDTO {
    /**
     * 具体的详细地址
     */
    private String address;

    /**
     * 城市地址详细信息
     */
    private AdInfoDTO ad_info;
}
