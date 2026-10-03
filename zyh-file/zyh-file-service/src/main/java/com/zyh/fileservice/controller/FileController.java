package com.zyh.fileservice.controller;

import com.zyh.commoncore.utils.BeanCopyUtil;
import com.zyh.commondomain.domain.R;
import com.zyh.fileservice.domain.dto.FileDTO;
import com.zyh.fileservice.domain.dto.SignDTO;
import com.zyh.fileservice.domain.vo.FileVO;
import com.zyh.fileservice.domain.vo.SignVO;
import com.zyh.fileservice.service.IFileService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhangyuheng
 */
@Slf4j
@RestController("/file")
public class FileController {

    @Resource(name = "OSSFileServiceImpl")
    private IFileService fileService;

    @PostMapping("/upload")
    public R<FileVO> upload(MultipartFile file) {
        FileDTO fileDTO = fileService.upload(file);
        FileVO fileVO = new FileVO();
        BeanCopyUtil.copyProperties(fileDTO, fileVO);
        return R.ok(fileVO);
    }

    @GetMapping("/sign")
    public R<SignVO> getSign() {
        SignDTO signDTO = fileService.getSign();
        SignVO signVO = new SignVO();
        BeanCopyUtil.copyProperties(signDTO, signVO);
        return R.ok(signVO);
    }
}
