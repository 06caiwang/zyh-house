package com.zyh.portalservice.house.controller;

import com.zyh.commondomain.domain.R;
import com.zyh.portalservice.house.domain.vo.HouseDataVO;
import com.zyh.portalservice.house.service.IHouseService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangyuheng
 */
@RestController
@RequestMapping("/housepage")
public class HousePageController {

    @Resource(name = "houseServiceImpl")
    private IHouseService houseService;

    /**
     * C端查询房源详情
     */
    @GetMapping("/get/nologin")
    public R<HouseDataVO> houseDetail(Long houseId) {
        return R.ok(houseService.houseDetail(houseId));
    }
}
