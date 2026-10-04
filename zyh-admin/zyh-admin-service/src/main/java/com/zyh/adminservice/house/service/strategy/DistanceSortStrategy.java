package com.zyh.adminservice.house.service.strategy;

import com.zyh.adminapi.house.domain.dto.SearchHouseListReqDTO;
import com.zyh.adminservice.house.domain.dto.HouseDTO;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangyuheng
 */
public class DistanceSortStrategy implements ISortStrategy {

    private static final DistanceSortStrategy INSTANCE = new DistanceSortStrategy();

    private DistanceSortStrategy() {}

    public static DistanceSortStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public List<HouseDTO> sort(List<HouseDTO> houseDTOList, SearchHouseListReqDTO reqDTO) {
        return houseDTOList.stream()
                .sorted(Comparator.comparingDouble(
                        houseDTO -> houseDTO.calculateDistance(reqDTO.getLongitude(), reqDTO.getLatitude()))
                ).collect(Collectors.toList());
    }
}
