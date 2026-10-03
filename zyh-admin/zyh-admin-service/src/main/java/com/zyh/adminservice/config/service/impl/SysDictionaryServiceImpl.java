package com.zyh.adminservice.config.service.impl;

import com.alibaba.nacos.shaded.com.google.common.collect.Maps;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyh.adminapi.config.domain.dto.*;
import com.zyh.adminapi.config.domain.vo.DictionaryDataVO;
import com.zyh.adminapi.config.domain.vo.DictionaryTypeVO;
import com.zyh.adminservice.config.domain.entity.SysDictionaryData;
import com.zyh.adminservice.config.domain.entity.SysDictionaryType;
import com.zyh.adminservice.config.mapper.SysDictionaryDataMapper;
import com.zyh.adminservice.config.mapper.SysDictionaryTypeMapper;
import com.zyh.adminservice.config.service.ISysDictionaryService;
import com.zyh.commondomain.domain.vo.BasePageVO;
import com.zyh.commondomain.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyuheng
 */
@Service
public class SysDictionaryServiceImpl implements ISysDictionaryService {

    @Autowired
    private SysDictionaryTypeMapper sysDictionaryTypeMapper;

    @Autowired
    private SysDictionaryDataMapper sysDictionaryDataMapper;

    @Override
    public Long addType(DictionaryTypeWriteReqDTO dictionaryTypeWriteReqDTO) {
        // 构造新增语句 select id from db where value = ? or type_key = ?;
        LambdaQueryWrapper<SysDictionaryType> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(SysDictionaryType::getId)
                .eq(SysDictionaryType::getValue, dictionaryTypeWriteReqDTO.getValue())
                .or()
                .eq(SysDictionaryType::getTypeKey, dictionaryTypeWriteReqDTO.getTypeKey());
        // 查询
        SysDictionaryType sysDictionaryType = sysDictionaryTypeMapper.selectOne(wrapper);
        // 判断是否重复
        if (sysDictionaryType != null) {
            throw new ServiceException("字典类型键或者值已存在");
        }
        // 插入操作
        sysDictionaryType = new SysDictionaryType();
        sysDictionaryType.setTypeKey(dictionaryTypeWriteReqDTO.getTypeKey());
        sysDictionaryType.setValue(dictionaryTypeWriteReqDTO.getValue());
        if (StringUtils.isNoneBlank(dictionaryTypeWriteReqDTO.getRemark())) {
            sysDictionaryType.setRemark(dictionaryTypeWriteReqDTO.getRemark());
        }
        int insert = sysDictionaryTypeMapper.insert(sysDictionaryType);
        // 是否插入成功
        if (insert <= 0) {
            throw new ServiceException("字典类型键新增错误");
        }
        // 返回入库中的id
        return sysDictionaryType.getId();
    }

    @Override
    public BasePageVO<DictionaryTypeVO> listType(DictionaryTypeListReqDTO dictionaryTypeListReqDTO) {
        BasePageVO<DictionaryTypeVO> result = new BasePageVO<>();
        // 构建查询字典类型列表的 SQL
        // select * from db where type_key = ? and value like '...%'
        LambdaQueryWrapper<SysDictionaryType> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(dictionaryTypeListReqDTO.getTypeKey())) {
            wrapper.eq(SysDictionaryType::getTypeKey, dictionaryTypeListReqDTO.getTypeKey());
        }
        if (StringUtils.isNotBlank(dictionaryTypeListReqDTO.getValue())) {
            wrapper.likeRight(SysDictionaryType::getValue, dictionaryTypeListReqDTO.getValue());
        }
        // 分页查询
        Page<SysDictionaryType> page = sysDictionaryTypeMapper.selectPage(
                new Page<>(dictionaryTypeListReqDTO.getPageNo().longValue(), dictionaryTypeListReqDTO.getPageSize().longValue()),
                wrapper
        );
        // 设置总记录数，页数
        result.setTotals(Integer.parseInt(String.valueOf(page.getTotal())));
        result.setTotalPages(Integer.parseInt(String.valueOf(page.getPages())));
        // 转换查到的结果类型 do -> vo
        List<DictionaryTypeVO> list = new ArrayList<>();
        for (SysDictionaryType sysDictionaryType : page.getRecords()) {
            DictionaryTypeVO dictionaryTypeVO = new DictionaryTypeVO();
            BeanUtils.copyProperties(sysDictionaryType, dictionaryTypeVO);
            list.add(dictionaryTypeVO);
        }
        result.setList(list);

        // 返回结果
        return result;
    }

    @Override
    public Long editType(DictionaryTypeWriteReqDTO dictionaryTypeWriteReqDTO) {
        // 构造查询语句
        // select * from db where type_key = ?;
        LambdaQueryWrapper<SysDictionaryType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictionaryType::getTypeKey, dictionaryTypeWriteReqDTO.getTypeKey());

        // 查询要修改的记录
        SysDictionaryType sysDictionaryType = sysDictionaryTypeMapper.selectOne(wrapper);

        // 校验
        if (sysDictionaryType == null) {
            throw new ServiceException("字典类型不存在");
        }
        if (sysDictionaryTypeMapper.selectOne(new LambdaQueryWrapper<SysDictionaryType>()
                .ne(SysDictionaryType::getTypeKey, dictionaryTypeWriteReqDTO.getTypeKey())
                .eq(SysDictionaryType::getValue, dictionaryTypeWriteReqDTO.getValue())) != null) {
            throw new ServiceException("字典类型名称已经存在");
        }
        // 更新
        sysDictionaryType.setValue(dictionaryTypeWriteReqDTO.getValue());
        sysDictionaryType.setRemark(dictionaryTypeWriteReqDTO.getRemark());
        sysDictionaryTypeMapper.updateById(sysDictionaryType);

        return sysDictionaryType.getId();
    }

    @Override
    public Long addData(DictionaryDataAddReqDTO dictionaryDataAddReqDTO) {
        // 创建查询 SQL, 校验记录
        // select * from db where type_key = ?;
        LambdaQueryWrapper<SysDictionaryType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictionaryType::getTypeKey, dictionaryDataAddReqDTO.getTypeKey());
        SysDictionaryType sysDictionaryType = sysDictionaryTypeMapper.selectOne(wrapper);
        if (sysDictionaryType == null) {
            throw new ServiceException("字典类型不存在");
        }

        // 构建查询 SQL
        // select * from db where value = ? or date_key = ?;
        LambdaQueryWrapper<SysDictionaryData> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(SysDictionaryData::getValue, dictionaryDataAddReqDTO.getValue())
                .or()
                .eq(SysDictionaryData::getDataKey, dictionaryDataAddReqDTO.getDataKey());
        SysDictionaryData sysDictionaryData = sysDictionaryDataMapper.selectOne(wrapper1);

        // 重复校验
        if (sysDictionaryData != null) {
            throw new ServiceException("字典数据键或值已存在");
        }

        // 新增字典数据的键值等
        sysDictionaryData = new SysDictionaryData();
        sysDictionaryData.setTypeKey(dictionaryDataAddReqDTO.getTypeKey());
        sysDictionaryData.setDataKey(dictionaryDataAddReqDTO.getDataKey());
        sysDictionaryData.setValue(dictionaryDataAddReqDTO.getValue());
        if (dictionaryDataAddReqDTO.getSort() != null) {
            sysDictionaryData.setSort(dictionaryDataAddReqDTO.getSort());
        }
        if (StringUtils.isNotBlank(dictionaryDataAddReqDTO.getRemark())) {
            sysDictionaryData.setRemark(dictionaryDataAddReqDTO.getRemark());
        }
        sysDictionaryDataMapper.insert(sysDictionaryData);

        return sysDictionaryData.getId();
    }

    @Override
    public BasePageVO<DictionaryDataVO> listData(DictionaryDataListReqDTO dictionaryDataListReqDTO) {
        // 空结果集
        BasePageVO<DictionaryDataVO> result = new BasePageVO<>();
        // 构造查询 SQL
        // select * from db where type_key = ? (and value like '...%') order by sort asc, id asc;
        LambdaQueryWrapper<SysDictionaryData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDictionaryData::getTypeKey, dictionaryDataListReqDTO.getTypeKey());
        if (StringUtils.isNotBlank(dictionaryDataListReqDTO.getValue())) {
            queryWrapper.likeRight(SysDictionaryData::getValue, dictionaryDataListReqDTO.getValue());
        }
        queryWrapper.orderByAsc(SysDictionaryData::getSort);
        queryWrapper.orderByAsc(SysDictionaryData::getId);
        // 分页查询
        Page<SysDictionaryData> page = sysDictionaryDataMapper.selectPage(
                new Page<>(dictionaryDataListReqDTO.getPageNo().longValue(), dictionaryDataListReqDTO.getPageSize().longValue()),
                queryWrapper
        );
        // 构建查询结果
        result.setTotals(((Long)page.getTotal()).intValue());
        result.setTotalPages(((Long)page.getPages()).intValue());
        List<DictionaryDataVO> list = new ArrayList<>();
        for (SysDictionaryData sysDictionaryData : page.getRecords()) {
            DictionaryDataVO dictionaryDataVO = new DictionaryDataVO();
            BeanUtils.copyProperties(sysDictionaryData, dictionaryDataVO);
            list.add(dictionaryDataVO);
        }
        result.setList(list);

        return result;
    }

    @Override
    public Long editData(DictionaryDataEditReqDTO dictionaryDataEditReqDTO) {
        // 构造查询SQL
        LambdaQueryWrapper<SysDictionaryData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictionaryData::getDataKey, dictionaryDataEditReqDTO.getDataKey());
        SysDictionaryData sysDictionaryData = sysDictionaryDataMapper.selectOne(wrapper);
        // 校验
        if (sysDictionaryData == null) {
            throw new ServiceException("字典数据不存在");
        }
        if (sysDictionaryDataMapper.selectOne(new LambdaQueryWrapper<SysDictionaryData>().ne(SysDictionaryData::getDataKey, dictionaryDataEditReqDTO.getDataKey()).eq(SysDictionaryData::getValue, dictionaryDataEditReqDTO.getValue())) != null) {
            throw new ServiceException("字典数据名称已存在");
        }
        // 部分属性选择性的修改
        sysDictionaryData.setValue(dictionaryDataEditReqDTO.getValue());
        if (dictionaryDataEditReqDTO.getSort() != null) {
            sysDictionaryData.setSort(dictionaryDataEditReqDTO.getSort());
        }
        if (StringUtils.isNotBlank(dictionaryDataEditReqDTO.getRemark())) {
            sysDictionaryData.setRemark(dictionaryDataEditReqDTO.getRemark());
        }
        sysDictionaryDataMapper.updateById(sysDictionaryData);

        return sysDictionaryData.getId();
    }

    @Override
    public List<DictionaryDataDTO> selectDictDataByType(String typeKey) {
        // 先查询数据表实体类
        List<SysDictionaryData> list = sysDictionaryDataMapper.selectList(new LambdaQueryWrapper<SysDictionaryData>().eq(SysDictionaryData::getTypeKey, typeKey));
        // 需要把数据表实体类对象转换成出参实体类对象
        List<DictionaryDataDTO> result = new ArrayList<>();
        for (SysDictionaryData sysDictionaryData : list) {
            DictionaryDataDTO dictionaryDataDTO = new DictionaryDataDTO();
            BeanUtils.copyProperties(sysDictionaryData, dictionaryDataDTO);
            result.add(dictionaryDataDTO);
        }

        return result;
    }

    @Override
    public Map<String, List<DictionaryDataDTO>> selectDictDataByTypes(List<String> typeKeys) {
        // 先把所有的字典数据查询出来
        List<SysDictionaryData> list = sysDictionaryDataMapper.selectList(
                new LambdaQueryWrapper<SysDictionaryData>().in(SysDictionaryData::getTypeKey, typeKeys));
        List<DictionaryDataDTO> result = new ArrayList<>();
        for (SysDictionaryData sysDictionaryData : list) {
            DictionaryDataDTO dictionaryDataDTO = new DictionaryDataDTO();
            BeanUtils.copyProperties(sysDictionaryData, dictionaryDataDTO);
            result.add(dictionaryDataDTO);
        }
        // 把查询出来的结果封装成哈希映射的形式
        Map<String, List<DictionaryDataDTO>> map = Maps.newHashMap();
        for (DictionaryDataDTO dictionaryDataDTO : result) {
            List<DictionaryDataDTO> value;
            // 先判断当前字典类型业务主键是否在哈希中
            if (map.get(dictionaryDataDTO.getTypeKey()) == null) {
                value = new ArrayList<>();
                value.add(dictionaryDataDTO);
                map.put(dictionaryDataDTO.getTypeKey(), value);
            } else {
                // 当前字典类型业务主键已经在哈希中
                value = map.get(dictionaryDataDTO.getTypeKey());
                value.add(dictionaryDataDTO);
            }
        }

        return map;
    }

    @Override
    public DictionaryDataDTO getDicDataByKey(String dataKey) {
        // 根据字典数据业务主键查询字典数据表实体类对象
        SysDictionaryData sysDictionaryData = sysDictionaryDataMapper.selectOne(
                new LambdaQueryWrapper<SysDictionaryData>().eq(SysDictionaryData::getDataKey, dataKey));
        // 做对象转换
        DictionaryDataDTO dictionaryDataDTO = new DictionaryDataDTO();
        BeanUtils.copyProperties(sysDictionaryData, dictionaryDataDTO);

        return dictionaryDataDTO;
    }

    @Override
    public List<DictionaryDataDTO> getDicDataByKeys(List<String> dataKeys) {
        // 根据字典数据业务主键列表查询字典数据表实体类对象列表
        List<SysDictionaryData> list = sysDictionaryDataMapper.selectList(
                new LambdaQueryWrapper<SysDictionaryData>().in(SysDictionaryData::getDataKey, dataKeys));
        // 做列表的对象转换
        List<DictionaryDataDTO> result = new ArrayList<>();
        for (SysDictionaryData sysDictionaryData : list) {
            DictionaryDataDTO dictionaryDataDTO = new DictionaryDataDTO();
            BeanUtils.copyProperties(sysDictionaryData, dictionaryDataDTO);
            result.add(dictionaryDataDTO);
        }

        return result;
    }
}
