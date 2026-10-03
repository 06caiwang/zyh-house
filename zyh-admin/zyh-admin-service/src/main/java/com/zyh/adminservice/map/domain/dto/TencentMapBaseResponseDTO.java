package com.zyh.adminservice.map.domain.dto;

import lombok.Data;

/**
 * @author zhangyuheng
 * 腾讯地图的响应
 */

@Data
public class TencentMapBaseResponseDTO {
    /**
     * 响应码  0表示成功
     */
    private Integer status;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 请求ID
     */
    private String request_id;
}
