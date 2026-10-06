package com.zyh.portalservice.homepage.controller;

import com.zyh.commondomain.domain.R;
import com.zyh.commondomain.domain.vo.BasePageVO;
import com.zyh.portalservice.homepage.domain.dto.HouseListReqDTO;
import com.zyh.portalservice.homepage.domain.dto.PullDataListReqDTO;
import com.zyh.portalservice.homepage.domain.vo.CityDescVO;
import com.zyh.portalservice.homepage.domain.vo.HouseDescVO;
import com.zyh.portalservice.homepage.domain.vo.PullDataListVO;
import com.zyh.portalservice.homepage.service.IHomePageService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 获取下拉筛选数据列表
     */
    @PostMapping("/pull_list/get/nologin")
    public R<PullDataListVO> getPullData(@Validated @RequestBody PullDataListReqDTO pullDataListReqDTO) {
        return R.ok(homePageService.getPullData(pullDataListReqDTO));
    }

    /**
     * 查询房源列表
     */
    @PostMapping("/house_list/search/nologin")
    public R<BasePageVO<HouseDescVO>> houseList(@Validated @RequestBody HouseListReqDTO reqDTO) {
        return R.ok(homePageService.houseList(reqDTO));
    }
}
