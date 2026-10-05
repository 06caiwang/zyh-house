package com.zyh.adminservice.house.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangyuheng
 */
@Data
public class HouseStatusEditReqDTO implements Serializable {
    /**
     * 房源Id
     */
    @NotNull(message = "房源Id不能为空！")
    private Long houseId;

    /**
     * 要修改的类型
     */
    @NotBlank(message = "要修改的类型不能为空！")
    private String status;

    /**
     * 出租时长
     */
    @NotBlank(message = "要修改的出租时长不能为空！")
    private String rentTimeCode;
}
