package com.zyh.portalservice.homepage.service;

import com.zyh.portalservice.homepage.domain.dto.CityDescDTO;

import java.util.List;

/**
 * @author zhangyuheng
 */
public interface IRegionService {

    List<CityDescDTO> regionChildren(Long parentId);
}
