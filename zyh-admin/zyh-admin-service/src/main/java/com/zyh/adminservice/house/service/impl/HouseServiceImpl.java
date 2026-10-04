package com.zyh.adminservice.house.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zyh.adminapi.config.domain.dto.DictionaryDataDTO;
import com.zyh.adminapi.house.domain.dto.DeviceDTO;
import com.zyh.adminapi.house.domain.dto.TagDTO;
import com.zyh.adminservice.config.service.ISysDictionaryService;
import com.zyh.adminservice.house.domain.HouseStatusEnum;
import com.zyh.adminservice.house.domain.dto.*;
import com.zyh.adminservice.house.domain.entity.*;
import com.zyh.adminservice.house.mapper.*;
import com.zyh.adminservice.house.service.IHouseService;
import com.zyh.adminservice.mapper.domain.entity.SysRegion;
import com.zyh.adminservice.mapper.mapper.RegionMapper;
import com.zyh.adminservice.user.domain.entity.AppUser;
import com.zyh.adminservice.user.mapper.AppUserMapper;
import com.zyh.commoncore.domain.dto.BasePageDTO;
import com.zyh.commoncore.utils.BeanCopyUtil;
import com.zyh.commoncore.utils.JsonUtil;
import com.zyh.commoncore.utils.TimestampUtil;
import com.zyh.commondomain.domain.ResultCode;
import com.zyh.commondomain.exception.ServiceException;
import com.zyh.commonredis.service.RedisService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author zhangyuheng
 */
@Slf4j
@Service
public class HouseServiceImpl implements IHouseService {

    // 城市房源映射 key 前缀
    private static final String CITY_HOUSE_PREFIX = "house:list:";
    // 城市完整信息 key 前缀
    private static final String HOUSE_PREFIX = "house:";

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private AppUserMapper appUserMapper;

    @Autowired
    private RegionMapper regionMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private TagHouseMapper tagHouseMapper;

    @Autowired
    private HouseStatusMapper houseStatusMapper;

    @Autowired
    private CityHouseMapper cityHouseMapper;

    @Autowired
    private RedisService redisService;

    @Resource(name = "sysDictionaryServiceImpl")
    private ISysDictionaryService sysDictionaryService;

    @Override
    public Long addOrEdit(HouseAddOrEditReqDTO houseAddOrEditReqDTO) {
        // 1.检验参数
        checkAddOrEditReq(houseAddOrEditReqDTO);

        // 2.设置房源基本信息
        House house = new House();
        house.setUserId(houseAddOrEditReqDTO.getUserId());
        house.setTitle(houseAddOrEditReqDTO.getTitle());
        house.setRentType(houseAddOrEditReqDTO.getRentType());
        house.setFloor(houseAddOrEditReqDTO.getFloor());
        house.setAllFloor(houseAddOrEditReqDTO.getAllFloor());
        house.setHouseType(houseAddOrEditReqDTO.getHouseType());
        house.setRooms(houseAddOrEditReqDTO.getRooms());
        house.setPosition(houseAddOrEditReqDTO.getPosition());
        house.setArea(BigDecimal.valueOf(houseAddOrEditReqDTO.getArea()));
        house.setPrice(BigDecimal.valueOf(houseAddOrEditReqDTO.getPrice()));
        house.setIntro(houseAddOrEditReqDTO.getIntro());
        // 表： soft,washer,broadband
        // 请求：["soft", "washer", "broadband"]
        house.setDevices(
                houseAddOrEditReqDTO.getDevices().stream()
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.joining(","))
        );
        house.setHeadImage(houseAddOrEditReqDTO.getHeadImage());
        // 存 JSON
        house.setImages(JsonUtil.obj2String(houseAddOrEditReqDTO.getImages()));
        house.setCityId(houseAddOrEditReqDTO.getCityId());
        house.setCityName(houseAddOrEditReqDTO.getCityName());
        house.setRegionId(houseAddOrEditReqDTO.getRegionId());
        house.setRegionName(houseAddOrEditReqDTO.getRegionName());
        house.setCommunityName(houseAddOrEditReqDTO.getCommunityName());
        house.setDetailAddress(houseAddOrEditReqDTO.getDetailAddress());
        house.setLongitude(BigDecimal.valueOf(houseAddOrEditReqDTO.getLongitude()));
        house.setLatitude(BigDecimal.valueOf(houseAddOrEditReqDTO.getLatitude()));


        // 4.编辑 需要判断是否更新 城市房源映射、标签房源映射
        // 4.1 编辑 MySQL(House TagHouse CityHouse)
        // 4.2 编辑 Redis(城市房源映射)
        if (null != houseAddOrEditReqDTO.getHouseId()) {
            house.setId(houseAddOrEditReqDTO.getHouseId());

            // 判断是否需要修改城市房源的映射
            House existHouse = houseMapper.selectById(houseAddOrEditReqDTO.getHouseId());
            if (cityHouseNeedChange(existHouse, houseAddOrEditReqDTO.getCityId())) {
                // 改变才更新(更新MySQL，更新Redis)
                editCityHouses(houseAddOrEditReqDTO.getHouseId(), existHouse.getCityId(),
                        houseAddOrEditReqDTO.getCityId(), houseAddOrEditReqDTO.getCityName());
            }

            // 判断是否需要修改标签房源的映射
            List<TagHouse> tagHouses = tagHouseMapper.selectList(
                    new LambdaQueryWrapper<TagHouse>()
                            .eq(TagHouse::getHouseId, houseAddOrEditReqDTO.getHouseId()));
            if (tagHouseNeedChange(tagHouses, houseAddOrEditReqDTO.getTagCodes())) {
                // 改变才更新(更新Mysql)
                editTagHouses(houseAddOrEditReqDTO.getHouseId(),
                        tagHouses, houseAddOrEditReqDTO.getTagCodes());
            }

        }

        // 如果是新增，调用完之后，house里会填充 id 字段
        houseMapper.insertOrUpdate(house);

        // 3.新增
        // 3.1 新增 MySQL(House HouseStatus TagHouse CityHouse)
        // 3.2 新增 Redis(城市房源映射)
        if (null == houseAddOrEditReqDTO.getHouseId()) {
            // 新增 HouseStatus TagHouse CityHouse：都需要一个 houseId 去做关联

            HouseStatus houseStatus = new HouseStatus();
            houseStatus.setHouseId(house.getId());
            houseStatus.setStatus(HouseStatusEnum.UP.name());
            houseStatusMapper.insert(houseStatus);

            // MySQL, Redis
            addCityHouse(house.getId(), house.getCityId(), house.getCityName());

            // MySQL
            addTagHouses(house.getId(), houseAddOrEditReqDTO.getTagCodes());

        }

        // 5.缓存房源完整信息 Redis(房源完整信息)
        cacheHouse(house.getId());
        return house.getId();
    }

    /**
     * 缓存房源完整信息
     *
     * @param houseId
     */
    @Override
    public void cacheHouse(Long houseId) {

        if (null == houseId) {
            log.warn("要缓存的房源id为空！");
            return;
        }

        // 通过id查询完整的信息
        HouseDTO houseDTO = getHouseDTObyId(houseId);
        if (null == houseDTO) {
            log.warn("缓存房源信息时，查询房源错误！");
            return;
        }

        // 缓存
        cacheHouse(houseDTO);
    }

    @Override
    public HouseDTO detail(Long houseId) {
        // houseId < 0 解决缓存穿透
        if (null == houseId || houseId < 0) {
            log.warn("要查询的房源id为空或无效！");
            return null;
        }

        // 1. 查询房源详情缓存
        HouseDTO houseDTO = getCacheHouse(houseId);

        // 2. 判断缓存是否存在
        if (null != houseDTO) {
            return houseDTO;
        }

        // 3. 缓存不存在，查询 Mysql
        houseDTO = getHouseDTObyId(houseId);

        // 4. mysql 不存在，缓存空对象（解决缓存穿透）
        if (null == houseDTO) {
            cacheNullHouse(houseId, 60L);
            log.error("查询房源信息错误，houseId:{}", houseId);
            return null;
        }

        // 5. mysql 存在，缓存房源详情
        cacheHouse(houseDTO);

        // 6. 返回
        return houseDTO;
    }

    @Override
    public BasePageDTO<HouseDescDTO> list(HouseListReqDTO houseListReqDTO) {
        BasePageDTO<HouseDescDTO> result = new BasePageDTO<>();

        // 查询总数：联表查询
        // 涉及 house_status、house 两张表
        Long totals = houseMapper.selectCountWithStatus(houseListReqDTO);
        if (0 == totals) {
            result.setTotals(0);
            result.setTotalPages(0);
            result.setList(Arrays.asList());
            log.info("查询的房源列表为空！HouseListReqDTO:{}", JsonUtil.obj2String(houseListReqDTO));
            return result;
        }

        // 查询列表
        List<HouseDescDTO> houses = houseMapper.selectPageWithStatus(houseListReqDTO);
        result.setTotals(
                Integer.parseInt(
                        String.valueOf(totals)));
        result.setTotalPages(
                BasePageDTO.calculateTotalPages(totals, houseListReqDTO.getPageSize()));
        if (CollectionUtils.isEmpty(houses)) {
            // 25
            // 3 10 正常情况
            // 4 10 异常情况
            log.info("超出查询房源列表范围！HouseListReqDTO:{}", JsonUtil.obj2String(houseListReqDTO));
            result.setList(Arrays.asList());
            return result;
        }
        result.setList(houses);
        return result;
    }

    @Override
    public void editStatus(HouseStatusEditReqDTO houseStatusEditReqDTO) {
        // 校验房源是否存在
        House house = houseMapper.selectById(houseStatusEditReqDTO.getHouseId());
        if (null == house) {
            throw new ServiceException("房源不存在，无法修改状态！");
        }

        // 校验状态，必须有状态（创建房源时，默认状态是上架）
        HouseStatus houseStatus = houseStatusMapper.selectOne(
                new LambdaQueryWrapper<HouseStatus>().eq(HouseStatus::getHouseId, house.getId()));
        if (null == houseStatus || StringUtils.isEmpty(houseStatus.getStatus())) {
            throw new ServiceException("房源状态不存在，无法修改状态！");
        }

        // 校验状态传参（status是枚举）
        HouseStatusEnum statusEnum = HouseStatusEnum.getByName(houseStatusEditReqDTO.getStatus());
        if (null == statusEnum) {
            throw new ServiceException("要修改的房源状态有误，无法修改状态！");
        }

        // 更新数据库(house_status)
        houseStatus.setStatus(houseStatusEditReqDTO.getStatus());
        if (HouseStatusEnum.RENTING.name()
                .equalsIgnoreCase(houseStatusEditReqDTO.getStatus())) {

            // 校验是否传了出租时长码
            if(StringUtils.isEmpty(houseStatusEditReqDTO.getRentTimeCode())) {
                throw new ServiceException("出租时长不能为空，无法修改状态！");
            }

            houseStatus.setRentTimeCode(houseStatusEditReqDTO.getRentTimeCode());
            houseStatus.setRentStartTime(TimestampUtil.getCurrentMillis());
            switch (houseStatusEditReqDTO.getRentTimeCode()) {
                case "one_year" -> houseStatus.setRentEndTime(TimestampUtil.getYearLaterMillis(1L));
                case "half_year" -> houseStatus.setRentEndTime(TimestampUtil.getMonthsLaterMillis(6L));
                case "thirty_seconds" -> houseStatus.setRentEndTime(TimestampUtil.getSecondsLaterMillis(30L));
                default -> throw new ServiceException("出租时长错误，无法修改状态！");
            }
        }

        houseStatusMapper.updateById(houseStatus);

        // 更新缓存
        cacheHouse(house.getId());
    }

    /**
     * 缓存房源空对象(带过期时间)
     *
     * @param houseId
     * @param timeout 秒
     */
    private void cacheNullHouse(Long houseId, Long timeout) {

        if (null == houseId) {
            log.warn("要缓存的房源id为空！");
            return;
        }

        // 缓存
        try {
            redisService.setCacheObject(HOUSE_PREFIX + houseId,
                    JsonUtil.obj2String(new HouseDTO()), timeout, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("缓存空房源完整信息时发生异常，houseId:{}", houseId, e);
            // 对于房源完整信息，是否存在于redis，不需要强一致性。
            // 因为C端查询时，如果redis不存在，可以通过查MySQL获取到数据，让后再放入Redis。
            // throw e;
        }

    }

    /**
     * 从缓存查询房源详情
     *
     * @param houseId
     * @return
     */
    private HouseDTO getCacheHouse(Long houseId) {
        if (null == houseId) {
            return null;
        }
        HouseDTO houseDTO = null;
        try {
            String houseDTOStr = redisService.getCacheObject(HOUSE_PREFIX + houseId, String.class);
            if (StringUtils.isBlank(houseDTOStr)) {
                return null;
            }
            houseDTO = JsonUtil.string2Obj(houseDTOStr, HouseDTO.class);
        } catch (Exception e) {
            log.error("从缓存中获取房源详情异常，key:{}", HOUSE_PREFIX + houseId, e);
        }

        return houseDTO;
    }


    /**
     * 根据房源id获取完整的房源信息
     *
     * @param houseId
     * @return
     */
    private HouseDTO getHouseDTObyId(Long houseId) {
        if (null == houseId) {
            log.warn("要查询的房源id为空");
            return null;
        }

        // 查房源、状态、tagHouse关联关系、房东信息
        House house = houseMapper.selectById(houseId);
        if (null == house) {
            log.error("查询房源失败，houseId:{}", houseId);
            return null;
        }

        AppUser appUser = appUserMapper.selectById(house.getUserId());
        if (null == appUser) {
            log.error("查询的房源房东信息不存在，houseId:{}, userId:{}", houseId, house.getUserId());
            return null;
        }

        HouseStatus houseStatus = houseStatusMapper.selectOne(
                new LambdaQueryWrapper<HouseStatus>()
                        .eq(HouseStatus::getHouseId, houseId));
        if (null == houseStatus) {
            log.error("查询的房源状态信息不存在，houseId:{}", houseId);
            return null;
        }

        List<TagHouse> tagHouses = tagHouseMapper.selectList(
                new LambdaQueryWrapper<TagHouse>().eq(TagHouse::getHouseId, houseId));


        // 组装完整的房源信息
        return convertToHouseDTO(house, houseStatus, appUser, tagHouses);
    }

    /**
     * 新增标签房源映射关系（MySQL）
     */
    private void addTagHouses(Long houseId, List<String> tagCodes) {
        List<TagHouse> tagHouses = tagCodes.stream()
                .map(tagCode -> {
                    TagHouse tagHouse = new TagHouse();
                    tagHouse.setTagCode(tagCode);
                    tagHouse.setHouseId(houseId);
                    return tagHouse;
                }).collect(Collectors.toList());
        tagHouseMapper.insert(tagHouses);
    }

    /**
     * 校验新增或编辑房源请求参数
     *
     * @param houseAddOrEditReqDTO
     */
    private void checkAddOrEditReq(HouseAddOrEditReqDTO houseAddOrEditReqDTO) {

        // 1. 校验房东信息
        AppUser appUser = appUserMapper.selectById(houseAddOrEditReqDTO.getUserId());
        if (null == appUser) {
            throw new ServiceException("房东id不存在！", ResultCode.INVALID_PARA.getCode());
        }

        // 2. 校验地址信息(城市id,区域id)
        List<Long> regionIds = Arrays.asList(
                houseAddOrEditReqDTO.getCityId(), houseAddOrEditReqDTO.getRegionId());
        List<SysRegion> regions = regionMapper.selectBatchIds(regionIds);
        if (regionIds.size() != regions.size()) {
            throw new ServiceException("传递的城市信息有误！", ResultCode.INVALID_PARA.getCode());
        }

        // 3. 校验标签码
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Tag::getTagCode, houseAddOrEditReqDTO.getTagCodes());
        List<Tag> tags = tagMapper.selectList(queryWrapper);
//        houseAddOrEditReqDTO.setTagCodes(
//                houseAddOrEditReqDTO.getTagCodes()
//                        .stream()
//                        .distinct()
//                        .collect(Collectors.toList())
//        );
        if (houseAddOrEditReqDTO.getTagCodes().size() != tags.size()) {
            throw new ServiceException("传递的标签列表有误！", ResultCode.INVALID_PARA.getCode());
        }
        // 4. 设备码、房源基本配置信息（字典）

    }

    /**
     * 新增城市房源映射关系（MySQL，Redis）
     */
    private void addCityHouse(Long houseId, Long cityId, String cityName) {
        CityHouse cityHouse = new CityHouse();
        cityHouse.setCityId(cityId);
        cityHouse.setCityName(cityName);
        cityHouse.setHouseId(houseId);
        cityHouseMapper.insert(cityHouse);

        // 新增城市房源列表缓存
        cacheCityHouses(1, houseId, null, cityId);

    }

    /**
     * 缓存城市房源映射关系
     */
    private void cacheCityHouses(int op, Long houseId,
                                 Long oldCityId, Long newCityId) {
        try {
            if (1 == op) {
                // 新增场景：新增城市下的房源id
                redisService.setCacheList(CITY_HOUSE_PREFIX + newCityId, Arrays.asList(houseId));
            } else if (2 == op) {
                // 修改场景：
                // 删除老城市下的房源
                redisService.removeForList(CITY_HOUSE_PREFIX + oldCityId, houseId);
                // 新增新城市下的房源
                redisService.setCacheList(CITY_HOUSE_PREFIX + newCityId, Arrays.asList(houseId));
            } else {
                log.error("无效的操作：缓存城市房源关联信息");
            }

        } catch (Exception e) {
            log.error("缓存城市下的房源列表发生异常，op:{}, houseId:{}, oldCityId:{}, newCityId:{}",
                    op, houseId, oldCityId, newCityId, e);
            // 注意这里抛出了异常，保证事务
            // 因为C端获取房源列表是以城市ID列表为主的，因此我们必须保证redis和mysql的数据的一致性！！！
            throw e;
        }

    }

    /**
     * 判断是否需要更新城市房源映射关系
     *
     * @param oldHouse
     * @param newCityId
     * @return
     */
    private boolean cityHouseNeedChange(House oldHouse, Long newCityId) {
        return !oldHouse.getCityId().equals(newCityId);
    }

    /**
     * 编辑城市房源映射（Mysql、Redis）
     *
     * @param houseId
     * @param oldCityId
     * @param newCityId
     * @param newCityName
     */
    private void editCityHouses(Long houseId, Long oldCityId,
                                Long newCityId, String newCityName) {

        // 删除老的映射记录
        cityHouseMapper.delete(new LambdaQueryWrapper<CityHouse>()
                .eq(CityHouse::getCityId, oldCityId)
                .eq(CityHouse::getHouseId, houseId));

        // 新增新的映射记录
        CityHouse cityHouse = new CityHouse();
        cityHouse.setHouseId(houseId);
        cityHouse.setCityId(newCityId);
        cityHouse.setCityName(newCityName);
        cityHouseMapper.insert(cityHouse);

        // 更新缓存
        cacheCityHouses(2, houseId, oldCityId, newCityId);
    }

    /**
     * 判断房源标签映射关系是否需要更新
     *
     * @param oldTagHouses
     * @param newTagCodes
     * @return
     */
    private boolean tagHouseNeedChange(List<TagHouse> oldTagHouses, List<String> newTagCodes) {
        List<String> oldTagCods = oldTagHouses.stream()
                .map(TagHouse::getTagCode)
                .sorted()  // 排序
                .collect(Collectors.toList());

        newTagCodes = newTagCodes.stream().sorted().collect(Collectors.toList());

        // 2 1 3
        // 1 3 2
        return !Objects.equals(oldTagCods, newTagCodes);

    }

    /**
     * 编辑房源标签映射关系
     *
     * @param houseId
     * @param oldTagHouses
     * @param newTagCodes
     */
    private void editTagHouses(Long houseId,
                               List<TagHouse> oldTagHouses,
                               List<String> newTagCodes) {
        // houseId: 1
        // old：1 2 3 4 5
        // new: 3 4 5 6 7

        // 需要过滤出要删除的 tagCodes 1 2
        Set<String> oldTagCodes = oldTagHouses.stream()
                .map(TagHouse::getTagCode)
                .collect(Collectors.toSet());
        List<String> deleteTagCodes = oldTagCodes.stream()
                .filter(oldTagCode -> !newTagCodes.contains(oldTagCode))
                .collect(Collectors.toList());

        // 删除需要删除的 tagCodes 1 2
        if (!CollectionUtils.isEmpty(deleteTagCodes)) {
            tagHouseMapper.delete(new LambdaQueryWrapper<TagHouse>()
                    .eq(TagHouse::getHouseId, houseId)
                    .in(TagHouse::getTagCode, deleteTagCodes));
        }

        // 过滤出要新增的 tagCodes  6 7
        List<TagHouse> newTagHouses = newTagCodes.stream()
                .filter(newTagCode -> !oldTagCodes.contains(newTagCode)) // 6 7 (String)
                .map(newTagCode -> {
                    TagHouse tagHouse = new TagHouse();
                    tagHouse.setTagCode(newTagCode);
                    tagHouse.setHouseId(houseId);
                    return tagHouse;
                }).collect(Collectors.toList());

        // 新增要新增的 tagCodes 6 7
        if (!CollectionUtils.isEmpty(newTagHouses)) {
            tagHouseMapper.insert(newTagHouses);
        }
    }

    /**
     * 组装房源完整信息
     *
     * @param house
     * @param houseStatus
     * @param appUser
     * @param tagHouses
     * @return
     */
    private HouseDTO convertToHouseDTO(House house, HouseStatus houseStatus,
                                       AppUser appUser, List<TagHouse> tagHouses) {
        // 校验数据合法性
        if (null == house || null == houseStatus || null == appUser) {
            log.warn("房源信息不完整！");
            return null;
        }

        HouseDTO houseDTO = new HouseDTO();
        BeanUtils.copyProperties(house, houseDTO);
        BeanUtils.copyProperties(houseStatus, houseDTO);
        BeanUtils.copyProperties(appUser, houseDTO);

        houseDTO.setArea(house.getArea().doubleValue());
        houseDTO.setPrice(house.getPrice().doubleValue());
        houseDTO.setLongitude(house.getLongitude().doubleValue());
        houseDTO.setLatitude(house.getLatitude().doubleValue());
        houseDTO.setImages(JsonUtil.string2List(house.getImages(), String.class));

        // 表： soft,washer,broadband
        // DeviceDTO:  String deviceCode，String deviceName;
        List<String> dataKeys = Arrays.stream(house.getDevices().split(","))
                .distinct()
                .collect(Collectors.toList());
        List<DictionaryDataDTO> deviceDataDTOS
                = sysDictionaryService.getDicDataByKeys(dataKeys);
        List<DeviceDTO> deviceDTOS = deviceDataDTOS.stream()
                .map(dataDTO -> {
                    DeviceDTO deviceDTO = new DeviceDTO();
                    deviceDTO.setDeviceCode(dataDTO.getDataKey());
                    deviceDTO.setDeviceName(dataDTO.getValue());
                    return deviceDTO;
                }).collect(Collectors.toList());
        houseDTO.setDevices(deviceDTOS);


        // TagDTO:String tagCode; String tagName;
        // 表 Tag

        // 获取到tagCodes，接着查询Tag
        List<String> tagCodes = tagHouses.stream()
                .map(TagHouse::getTagCode)
                .distinct()
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(tagCodes)) {
            List<Tag> tags = tagMapper.selectList(
                    new LambdaQueryWrapper<Tag>().in(Tag::getTagCode, tagCodes));
            houseDTO.setTags(BeanCopyUtil.copyListProperties(tags, TagDTO::new));
        }

        return houseDTO;
    }

    /**
     * 缓存房源完整数据 houseDTO
     *
     * @param houseDTO
     */
    private void cacheHouse(HouseDTO houseDTO) {
        if (null == houseDTO) {
            log.warn("要缓存的房源详细信息为空！");
            return;
        }

        // 缓存
        try {
            redisService.setCacheObject(HOUSE_PREFIX + houseDTO.getHouseId(),
                    JsonUtil.obj2String(houseDTO));
        } catch (Exception e) {
            log.error("缓存房源完整信息时发生异常，houseDTO:{}", JsonUtil.obj2String(houseDTO), e);
            // 对于房源完整信息，是否存在于redis，不需要强一致性。
            // 因为C端查询时，如果redis不存在，可以通过查MySQL获取到数据，让后再放入Redis。
            // throw e;
        }

    }
}