package com.zyh.chatservice.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zyh.commoncore.domain.entity.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyuheng
 */
@Data
@TableName(value = "session")
@EqualsAndHashCode(callSuper = true)
public class Session extends BaseDO {
    private Long userId1;
    private Long userId2;
}
