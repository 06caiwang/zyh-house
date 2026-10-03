package com.zyh.adminservice.config.controller;

import com.zyh.adminapi.config.domain.dto.ArgumentAddReqDTO;
import com.zyh.adminapi.config.domain.dto.ArgumentDTO;
import com.zyh.adminapi.config.domain.dto.ArgumentEditReqDTO;
import com.zyh.adminapi.config.domain.dto.ArgumentListReqDTO;
import com.zyh.adminapi.config.domain.vo.ArgumentVO;
import com.zyh.adminapi.config.feign.ArgumentFeignClient;
import com.zyh.adminservice.config.service.ISysArgumentService;
import com.zyh.commoncore.utils.JsonUtil;
import com.zyh.commondomain.domain.R;
import com.zyh.commondomain.domain.vo.BasePageVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangyuheng
 */
@Slf4j
@RestController
@RequestMapping("/argument")
public class ArgumentController implements ArgumentFeignClient {

    @Resource(name = "sysArgumentServiceImpl")
    private ISysArgumentService iSysArgumentService;

    /**
     * 新增参数
     * @param argumentAddReqDTO 新增参数请求DTO
     * @return Long
     */
    @PostMapping("/add")
    public R<Long> add(@RequestBody @Validated ArgumentAddReqDTO argumentAddReqDTO) {
        // 打印日志
        log.info("add ArgumentAddReqDTO: {}", JsonUtil.obj2String(argumentAddReqDTO));
        // 调用service，返回结果
        return R.ok(iSysArgumentService.add(argumentAddReqDTO));
    }

    /**
     * 参数列表
     * @param argumentListReqDTO 查看参数DTO
     * @return BasePageVO
     */
    @GetMapping("/list")
    public R<BasePageVO<ArgumentVO>> list(@Validated ArgumentListReqDTO argumentListReqDTO) {
        // 打印日志
        log.info("list ArgumentListReqDTO: {}", JsonUtil.obj2String(argumentListReqDTO));
        // 调用service，返回结果
        return R.ok(iSysArgumentService.list(argumentListReqDTO));
    }

    /**
     * 编辑参数
     * @param argumentEditReqDTO 编辑参数DTO
     * @return Long
     */
    @PostMapping("/edit")
    public R<Long> edit(@RequestBody @Validated ArgumentEditReqDTO argumentEditReqDTO) {
        // 打印日志
        log.info("edit ArgumentEditReqDTO: {}", JsonUtil.obj2String(argumentEditReqDTO));
        // 调用service，返回结果
        return R.ok(iSysArgumentService.edit(argumentEditReqDTO));
    }

    @Override
    public ArgumentDTO getByConfigKey(String configKey) {
        // 打印日志
        log.info("getByConfigKey configKey: {}", configKey);
        // 调用service，返回结果
        return iSysArgumentService.getByConfigKey(configKey);
    }

    @Override
    public List<ArgumentDTO> getByConfigKeys(List<String> configKeys) {
        // 打印日志
        log.info("getByConfigKeys configKeys: {}", JsonUtil.obj2String(configKeys));
        // 调用service，返回结果
        return iSysArgumentService.getByConfigKeys(configKeys);
    }
}
