package com.zyh.portalservice.homepage.service.impl;

import com.zyh.adminapi.house.domain.dto.SearchHouseListReqDTO;
import com.zyh.adminapi.house.domain.vo.HouseDetailVO;
import com.zyh.adminapi.house.feign.HouseFeignClient;
import com.zyh.commondomain.domain.R;
import com.zyh.commondomain.domain.vo.BasePageVO;
import com.zyh.commondomain.exception.ServiceException;
import com.zyh.portalservice.homepage.domain.dto.DictDataDTO;
import com.zyh.portalservice.homepage.domain.dto.HouseListReqDTO;
import com.zyh.portalservice.homepage.domain.vo.HouseDescVO;
import com.zyh.portalservice.homepage.service.IDictionaryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HomePageServiceImplTest {
    @Mock
    private HouseFeignClient houseFeignClient;
    @Mock
    private IDictionaryService dictionaryService;
    @InjectMocks
    private HomePageServiceImpl service;

    @Test
    void validDictionariesPreserveDisplayFieldsAndPagination() {
        stubPage(List.of(house(101L, "whole_rent", "south")), 23, 3);
        when(dictionaryService.batchFindDictionaryData(anyList())).thenReturn(Map.of(
                "whole_rent", dictionary("whole_rent", "整租"),
                "south", dictionary("south", "南")));

        BasePageVO<HouseDescVO> result = service.houseList(request());

        assertThat(result.getTotals()).isEqualTo(23);
        assertThat(result.getTotalPages()).isEqualTo(3);
        assertThat(result.getList()).singleElement().satisfies(item -> {
            assertThat(item.getHouseId()).isEqualTo(101L);
            assertThat(item.getTitle()).isEqualTo("测试房源101");
            assertThat(item.getHeadImage()).isEqualTo("https://example.invalid/house.png");
            assertThat(item.getArea()).isEqualTo(65.0);
            assertThat(item.getPrice()).isEqualTo(2600.0);
            assertThat(item.getRegionName()).isEqualTo("测试区域");
            assertThat(item.getRentType()).isEqualTo("整租");
            assertThat(item.getPosition()).isEqualTo("南");
        });
        ArgumentCaptor<SearchHouseListReqDTO> query = ArgumentCaptor.forClass(SearchHouseListReqDTO.class);
        verify(houseFeignClient).searchList(query.capture());
        assertThat(query.getValue().getCityId()).isEqualTo(122L);
        assertThat(query.getValue().getPageNo()).isEqualTo(2);
        assertThat(query.getValue().getPageSize()).isEqualTo(10);
        assertThat(query.getValue().getSort()).isEqualTo("price_asc");
    }

    @Test
    void missingSoutheastKeepsBothAffectedAndNormalHouses() {
        stubPage(List.of(house(101L, "whole_rent", "southeast"),
                house(102L, "whole_rent", "south")), 2, 1);
        when(dictionaryService.batchFindDictionaryData(anyList())).thenReturn(Map.of(
                "whole_rent", dictionary("whole_rent", "整租"),
                "south", dictionary("south", "南")));

        BasePageVO<HouseDescVO> result = service.houseList(request());

        assertThat(result.getTotals()).isEqualTo(2);
        assertThat(result.getList()).extracting(HouseDescVO::getHouseId).containsExactly(101L, 102L);
        assertThat(result.getList()).extracting(HouseDescVO::getPosition).containsExactly("朝向待确认", "南");
        assertThat(result.getList()).extracting(HouseDescVO::getRentType).containsExactly("整租", "整租");
    }

    @Test
    void missingRentTypeKeepsKnownPosition() {
        stubPage(List.of(house(101L, "unknown_rent", "south")), 1, 1);
        when(dictionaryService.batchFindDictionaryData(anyList())).thenReturn(Map.of(
                "south", dictionary("south", "南")));

        assertThat(service.houseList(request()).getList()).singleElement().satisfies(item -> {
            assertThat(item.getRentType()).isEqualTo("租赁方式待确认");
            assertThat(item.getPosition()).isEqualTo("南");
        });
    }

    @Test
    void missingBothDictionariesStillReturnsHouse() {
        stubPage(List.of(house(101L, "unknown_rent", "southeast")), 1, 1);
        when(dictionaryService.batchFindDictionaryData(anyList())).thenReturn(Map.of());

        assertThat(service.houseList(request()).getList()).singleElement().satisfies(item -> {
            assertThat(item.getHouseId()).isEqualTo(101L);
            assertThat(item.getRentType()).isEqualTo("租赁方式待确认");
            assertThat(item.getPosition()).isEqualTo("朝向待确认");
        });
    }

    @Test
    void emptyCityReturnsEmptyPageWithoutDictionaryRequest() {
        stubPage(List.of(), 0, 0);

        BasePageVO<HouseDescVO> result = service.houseList(request());

        assertThat(result.getList()).isEmpty();
        assertThat(result.getTotals()).isZero();
        verifyNoInteractions(dictionaryService);
    }

    @Test
    void upstreamFailureRemainsAnErrorRatherThanAnEmptySuccess() {
        when(houseFeignClient.searchList(any())).thenReturn(R.fail("上游服务失败"));

        assertThatThrownBy(() -> service.houseList(request()))
                .isInstanceOf(ServiceException.class).hasMessage("查询房源列表失败！");
        verifyNoInteractions(dictionaryService);
    }

    private void stubPage(List<HouseDetailVO> houses, int total, int pages) {
        BasePageVO<HouseDetailVO> page = new BasePageVO<>();
        page.setList(houses);
        page.setTotals(total);
        page.setTotalPages(pages);
        when(houseFeignClient.searchList(any())).thenReturn(R.ok(page));
    }

    private HouseListReqDTO request() {
        HouseListReqDTO request = new HouseListReqDTO();
        request.setCityId(122L);
        request.setPageNo(2);
        request.setPageSize(10);
        request.setSort("price_asc");
        request.setLongitude(120.15);
        request.setLatitude(30.25);
        return request;
    }

    private HouseDetailVO house(long id, String rentType, String position) {
        HouseDetailVO house = new HouseDetailVO();
        house.setHouseId(id);
        house.setRentType(rentType);
        house.setPosition(position);
        house.setTitle("测试房源" + id);
        house.setHeadImage("https://example.invalid/house.png");
        house.setArea(65.0);
        house.setPrice(2600.0);
        house.setRegionName("测试区域");
        return house;
    }

    private DictDataDTO dictionary(String key, String value) {
        DictDataDTO dictionary = new DictDataDTO();
        dictionary.setDataKey(key);
        dictionary.setValue(value);
        return dictionary;
    }
}
