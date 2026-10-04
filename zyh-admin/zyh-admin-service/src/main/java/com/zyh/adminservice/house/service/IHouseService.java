package com.zyh.adminservice.house.service;

import com.zyh.adminservice.house.domain.dto.*;
import com.zyh.commoncore.domain.dto.BasePageDTO;

/**
 * @author zhangyuheng
 */
public interface IHouseService {
    /**
     * 新增或编辑房源
     *
     * @param houseAddOrEditReqDTO
     * @return
     */
    Long addOrEdit(HouseAddOrEditReqDTO houseAddOrEditReqDTO);

    /**
     * 更新房源缓存
     *
     * @param houseId
     */
    void cacheHouse(Long houseId);

    /**
     * 查询房源详情（带缓存）
     *
     * @param houseId
     * @return
     */
    HouseDTO detail(Long houseId);

    /**
     * 查询房源摘要列表（支持筛选、翻页）
     *
     * @param houseListReqDTO
     * @return
     */
    BasePageDTO<HouseDescDTO> list(HouseListReqDTO houseListReqDTO);

    /**
     * 修改房源状态
     *
     * @param houseStatusEditReqDTO
     */
    void editStatus(HouseStatusEditReqDTO houseStatusEditReqDTO);

    /**
     * 脚本：刷新全量缓存
     */
    void refreshHouseIds();
}
