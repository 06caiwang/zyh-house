package com.zyh.adminapi.house.feign;

import com.zyh.adminapi.house.domain.dto.SearchHouseListReqDTO;
import com.zyh.adminapi.house.domain.vo.HouseDetailVO;
import com.zyh.commondomain.domain.R;
import com.zyh.commondomain.domain.vo.BasePageVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhangyuheng
 */
@FeignClient(contextId = "houseFeignClient", value = "zyh-admin", path = "/house")
public interface HouseFeignClient {
    /**
     * 查询房源详情（带缓存）
     */
    @GetMapping("/detail")
    R<HouseDetailVO> detail(@RequestParam Long houseId);
}
