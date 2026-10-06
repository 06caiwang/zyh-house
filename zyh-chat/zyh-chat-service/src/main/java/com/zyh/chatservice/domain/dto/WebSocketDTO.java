package com.zyh.chatservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangyuheng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketDTO<T> {
    private String type;

    private T data;

}
