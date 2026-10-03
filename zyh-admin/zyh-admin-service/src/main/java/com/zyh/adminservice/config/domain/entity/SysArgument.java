package com.zyh.adminservice.config.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zhangyuheng
 */
@Data
@TableName(value = "sys_argument")
public class SysArgument {
    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 参数名称
     */
    private String name;

    /**
     * 参数业务主键
     */
    private String configKey;

    /**
     * 参数值
     */
    private String value;

    /**
     * 备注
     */
    private String remark;
}
