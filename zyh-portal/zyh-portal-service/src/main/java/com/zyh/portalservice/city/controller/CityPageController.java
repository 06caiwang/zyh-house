package com.zyh.portalservice.city.controller;

import com.zyh.commondomain.domain.R;
import com.zyh.portalservice.city.domain.vo.CityPageVO;
import com.zyh.portalservice.city.service.ICityService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangyuheng
 */
@RestController
@RequestMapping("/citypage")
public class CityPageController {

    @Resource(name = "cityServiceImpl")
    private ICityService cityService;

    /**
     * 查询热门城市与全城市列表
     */
    @GetMapping("/get/nologin")
    public R<CityPageVO> cityPage() {
        return R.ok(cityService.getCityPage());
    }
}