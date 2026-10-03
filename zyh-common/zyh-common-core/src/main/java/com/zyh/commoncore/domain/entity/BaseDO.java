package com.zyh.commoncore.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author zhangyuheng
 * DO基类
 */
@Data
public class BaseDO {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

}