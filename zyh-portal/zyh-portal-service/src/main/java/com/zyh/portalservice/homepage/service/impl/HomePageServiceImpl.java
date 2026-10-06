package com.zyh.portalservice.homepage.service.impl;

import com.zyh.adminapi.map.domain.dto.LocationReqDTO;
import com.zyh.adminapi.map.domain.vo.CityVO;
import com.zyh.adminapi.map.feigen.MapFeignClient;
import com.zyh.commoncore.utils.BeanCopyUtil;
import com.zyh.commondomain.domain.R;
import com.zyh.commondomain.domain.ResultCode;
import com.zyh.commondomain.exception.ServiceException;
import com.zyh.portalservice.homepage.domain.dto.CityDescDTO;
import com.zyh.portalservice.homepage.domain.dto.DictDataDTO;
import com.zyh.portalservice.homepage.domain.dto.PullDataListReqDTO;
import com.zyh.portalservice.homepage.domain.vo.CityDescVO;
import com.zyh.portalservice.homepage.domain.vo.DictsVO;
import com.zyh.portalservice.homepage.domain.vo.PullDataListVO;
import com.zyh.portalservice.homepage.service.IDictionaryService;
import com.zyh.portalservice.homepage.service.IHomePageService;
import com.zyh.portalservice.homepage.service.IRegionService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangyuheng
 */
@Slf4j
@Service
public class HomePageServiceImpl implements IHomePageService {

    @Autowired
    private MapFeignClient mapFeignClient;

    @Resource(name = "regionServiceImpl")
    private IRegionService regionService;

    @Resource(name = "dictionaryServiceImpl")
    private IDictionaryService dictionaryService;

    @Override
    public CityDescVO getCityDesc(Double lat, Double lng) {
        // 校验参数
        if(null == lat || null == lng) {
            throw new ServiceException("城市经纬度不能为空！", ResultCode.INVALID_PARA.getCode());
        }

        // 发起远程调用：map服务
        LocationReqDTO locationReqDTO = new LocationReqDTO();
        locationReqDTO.setLat(lat);
        locationReqDTO.setLng(lng);
        R<CityVO> result = mapFeignClient.locateCityByLocation(locationReqDTO);
        if (null == result
                || result.getCode() != ResultCode.SUCCESS.getCode()
                || null == result.getData()) {
            throw new ServiceException("根据定位获取城市信息失败！");
        }

        // 构造返回
        CityDescVO cityDescVO = new CityDescVO();
        BeanUtils.copyProperties(result.getData(), cityDescVO);
        return cityDescVO;
    }

    @Override
    public PullDataListVO getPullData(PullDataListReqDTO pullDataListReqDTO) {
        PullDataListVO result = new PullDataListVO();

        // 查询城市下区域列表
        List<CityDescDTO> cityDescDTOList =
                regionService.regionChildren(pullDataListReqDTO.getCityId());
        result.setRegionList(
                BeanCopyUtil.copyListProperties(cityDescDTOList, CityDescVO::new));


        // 查询字典数据列表
        Map<String, List<DictDataDTO>> dictDataMap =
                dictionaryService.batchFindDictionaryDataByTypes(pullDataListReqDTO.getDirtTypes());
        Map<String, List<DictsVO>> dictMap = new HashMap<>();
        for (Map.Entry<String, List<DictDataDTO>> entry : dictDataMap.entrySet()) {
            List<DictsVO> list = entry.getValue().stream()
                    .map(dictDataDTO -> {
                        DictsVO dictsVO = new DictsVO();
                        dictsVO.setId(dictDataDTO.getId());
                        dictsVO.setKey(dictDataDTO.getDataKey());
                        dictsVO.setName(dictDataDTO.getValue());
                        return dictsVO;
                    }).collect(Collectors.toList());
            dictMap.put(entry.getKey(), list);
        }
        result.setDictMap(dictMap);

        // 构造返回
        return result;
    }
}
