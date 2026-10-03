package com.zyh.adminservice.house.controller;

import com.zyh.adminservice.house.domain.dto.HouseAddOrEditReqDTO;
import com.zyh.adminservice.house.service.IHouseService;
import com.zyh.commondomain.domain.R;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangyuheng
 */
@Slf4j
@RestController
@RequestMapping("/house")
public class HouseController {

    @Resource(name = "houseServiceImpl")
    private IHouseService houseService;

    /**
     * 新增或编辑房源
     */
    @PostMapping("/add_edit")
    public R<Long> addOrEdit(@Validated @RequestBody HouseAddOrEditReqDTO houseAddOrEditReqDTO) {
        Long houseId = houseService.addOrEdit(houseAddOrEditReqDTO);
        return R.ok(houseId);
    }
}
