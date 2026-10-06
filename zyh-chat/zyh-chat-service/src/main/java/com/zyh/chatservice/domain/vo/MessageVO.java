package com.zyh.chatservice.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangyuheng
 */
@Data
public class MessageVO implements Serializable {
    private String messageId;
    private Long sessionId;
    private Long fromId;
    private String content;
    private Integer type;
    private Integer status;
    private Integer visited;
    private Long createTime;
}
