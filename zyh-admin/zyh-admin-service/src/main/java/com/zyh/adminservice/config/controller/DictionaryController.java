package com.zyh.adminservice.config.controller;

import com.zyh.adminapi.config.domain.dto.*;
import com.zyh.adminapi.config.domain.vo.DictionaryDataVO;
import com.zyh.adminapi.config.domain.vo.DictionaryTypeVO;
import com.zyh.adminapi.config.feign.DictionaryFeignClient;
import com.zyh.adminservice.config.service.ISysDictionaryService;
import com.zyh.commoncore.utils.JsonUtil;
import com.zyh.commondomain.domain.R;
import com.zyh.commondomain.domain.vo.BasePageVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyuheng
 */
@Slf4j
@RestController
public class DictionaryController implements DictionaryFeignClient {

    @Resource(name = "sysDictionaryServiceImpl")
    private ISysDictionaryService iSysDictionaryService;

    /**
     * 新增字典类型
     * @param dictionaryTypeWriteReqDTO 新增字典类型DTO
     * @return Long
     */
    @PostMapping("/dictionary_type/add")
    public R<Long> addType(@RequestBody @Validated DictionaryTypeWriteReqDTO dictionaryTypeWriteReqDTO) {
        // 打印日志
        log.info("addType DictionaryTypeWriteReqDTO: {}", JsonUtil.obj2String(dictionaryTypeWriteReqDTO));
        // 调用service
        Long response = iSysDictionaryService.addType(dictionaryTypeWriteReqDTO);
        // 返回结果
        return R.ok(response);
    }

    /**
     * 字典类型列表
     * @param dictionaryTypeListReqDTO 字典类型列表DTO
     * @return BasePageVO
     */
    @GetMapping("/dictionary_type/list")
    public R<BasePageVO<DictionaryTypeVO>> listType(@Validated DictionaryTypeListReqDTO dictionaryTypeListReqDTO) {
        // 打印日志
        log.info("listType DictionaryTypeListReqDTO: {}", JsonUtil.obj2String(dictionaryTypeListReqDTO));
        // 调用service并返回
        return R.ok(iSysDictionaryService.listType(dictionaryTypeListReqDTO));
    }

    /**
     * 编辑字典类型
     * @param dictionaryTypeWriteReqDTO 编辑字典类型DTO
     * @return Long
     */
    @PostMapping("/dictionary_type/edit")
    public R<Long> editType(@RequestBody @Validated DictionaryTypeWriteReqDTO dictionaryTypeWriteReqDTO) {
        // 打印日志
        log.info("editType DictionaryTypeWriteReqDTO: {}", JsonUtil.obj2String(dictionaryTypeWriteReqDTO));
        // 调用service，并返回结果
        return R.ok(iSysDictionaryService.editType(dictionaryTypeWriteReqDTO));
    }

    /**
     * 新增字典数据
     * @param dictionaryDataAddReqDTO 新增字典数据DTO
     * @return Long
     */
    @PostMapping("/dictionary_data/add")
    public R<Long> addData(@RequestBody @Validated DictionaryDataAddReqDTO dictionaryDataAddReqDTO) {
        // 打印日志
        log.info("addData DictionaryDataAddReqDTO: {}", JsonUtil.obj2String(dictionaryDataAddReqDTO));
        // 调用service，并返回结果
        return R.ok(iSysDictionaryService.addData(dictionaryDataAddReqDTO));
    }

    /**
     * 获取字典数据列表
     * @param dictionaryDataListReqDTO 字典数据列表DTO
     * @return BasePageVO
     */
    @GetMapping("/dictionary_data/list")
    public R<BasePageVO<DictionaryDataVO>> listData(@Validated DictionaryDataListReqDTO dictionaryDataListReqDTO) {
        // 打印日志
        log.info("listData DictionaryDataListReqDTO: {}", JsonUtil.obj2String(dictionaryDataListReqDTO));
        // 调用service，并返回结果
        return R.ok(iSysDictionaryService.listData(dictionaryDataListReqDTO));
    }

    /**
     * 编辑字典数据
     * @param dictionaryDataEditReqDTO 编辑字典数据DTO
     * @return Long
     */
    @PostMapping("/dictionary_data/edit")
    public R<Long> editData(@RequestBody @Validated DictionaryDataEditReqDTO dictionaryDataEditReqDTO) {
        // 打印日志
        log.info("editData DictionaryDataEditReqDTO: {}", JsonUtil.obj2String(dictionaryDataEditReqDTO));
        // 调用service，并返回结果
        return R.ok(iSysDictionaryService.editData(dictionaryDataEditReqDTO));
    }

    @Override
    public List<DictionaryDataDTO> selectDictDataByType(String typeKey) {
        // 打印日志
        log.info("selectDictDataByType typeKey: {}", typeKey);
        // 调用service，并返回结果
        return iSysDictionaryService.selectDictDataByType(typeKey);
    }

    @Override
    public Map<String, List<DictionaryDataDTO>> selectDictDataByTypes(List<String> typeKeys) {
        // 打印日志
        log.info("selectDictDataByTypes typeKeys: {}", JsonUtil.obj2String(typeKeys));
        // 调用service，并返回结果
        return iSysDictionaryService.selectDictDataByTypes(typeKeys);
    }

    @Override
    public DictionaryDataDTO getDicDataByKey(String dataKey) {
        // 打印日志
        log.info("getDicDataByKey dataKey: {}", dataKey);
        // 调用service，并返回结果
        return iSysDictionaryService.getDicDataByKey(dataKey);
    }

    @Override
    public List<DictionaryDataDTO> getDicDataByKeys(List<String> dataKeys) {
        // 打印日志
        log.info("getDicDataByKeys dataKeys: {}", JsonUtil.obj2String(dataKeys));
        // 调用service，并返回结果
        return iSysDictionaryService.getDicDataByKeys(dataKeys);
    }
}
