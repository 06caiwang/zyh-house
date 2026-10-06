package com.zyh.portalservice.city.domain.vo;

import com.zyh.portalservice.homepage.domain.vo.CityDescVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyuheng
 */
@Data
public class CityPageVO implements Serializable {
    /**
     * 热门城市列表
     */
    private List<CityDescVO> hotCityList;

    /**
     * a-z 城市列表
     */
    private Map<String, List<CityDescVO>> allCityMap;
}
