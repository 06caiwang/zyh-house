package com.zyh.adminservice.house.controller;

import com.zyh.adminapi.house.domain.vo.HouseDetailVO;
import com.zyh.adminapi.house.feign.HouseFeignClient;
import com.zyh.adminservice.house.domain.dto.*;
import com.zyh.adminservice.house.domain.vo.HouseVO;
import com.zyh.adminservice.house.service.IHouseService;
import com.zyh.commoncore.domain.dto.BasePageDTO;
import com.zyh.commondomain.domain.R;
import com.zyh.commondomain.domain.vo.BasePageVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangyuheng
 */
@Slf4j
@RestController
@RequestMapping("/house")
public class HouseController implements HouseFeignClient {

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

    /**
     * 查询房源详情（带缓存）
     */
    @GetMapping("/detail")
    @Override
    public R<HouseDetailVO> detail(Long houseId) {
        HouseDTO houseDTO = houseService.detail(houseId);
        if (null == houseDTO) {
            log.warn("要查询的房源不存在，houseId:{}", houseId);
            return R.fail("房源详情不存在！");
        }
        return R.ok(houseDTO.convertToVO());
    }

    /**
     * 查询房源摘要列表（支持翻页、支持筛选）
     */
    @PostMapping("/list")
    public R<BasePageVO<HouseVO>> list(@Validated @RequestBody HouseListReqDTO houseListReqDTO) {
        BasePageDTO<HouseDescDTO> houseDescList = houseService.list(houseListReqDTO);
        BasePageVO<HouseVO> result = new BasePageVO<>();
        BeanUtils.copyProperties(houseDescList, result);
        return R.ok(result);
    }

    /**
     * 更新房源状态
     */
    @PostMapping("/status/edit")
    public R<?> editStatus(@Validated @RequestBody HouseStatusEditReqDTO houseStatusEditReqDTO) {
        houseService.editStatus(houseStatusEditReqDTO);
        return R.ok();
    }
}
