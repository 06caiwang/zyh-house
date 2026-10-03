package com.zyh.adminapi.map.domain.dto;



import com.zyh.commoncore.domain.BasePageReqDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyuheng
 * 根据地点查询的请求参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PlaceSearchReqDTO extends BasePageReqDTO {
    /**
     * 请求的关键字
     */
    @NotNull(message = "请求关键字不允许为空")
    private String keyword;

    /**
     * 请求区域ID
     */
    @NotNull(message = "请求区域ID不能为空")
    private Long id;
}
