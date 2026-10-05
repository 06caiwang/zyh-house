package com.zyh.portalservice.homepage.controller;

import com.zyh.commondomain.domain.R;
import com.zyh.portalservice.homepage.domain.vo.CityDescVO;
import com.zyh.portalservice.homepage.service.IHomePageService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangyuheng
 */
@RestController
@RequestMapping("/homepage")
public class HomePageController  {

    @Resource(name = "homePageServiceImpl")
    private IHomePageService homePageService;

    /**
     * 根据经纬度获取城市信息
     */
    @GetMapping("/city_desc/get/nologin")
    public R<CityDescVO> getCityDesc(Double lat, Double lng) {
        return R.ok(homePageService.getCityDesc(lat, lng));
    }
}
