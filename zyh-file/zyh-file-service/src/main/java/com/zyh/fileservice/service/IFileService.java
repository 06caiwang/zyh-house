package com.zyh.fileservice.service;

import com.zyh.fileservice.domain.dto.FileDTO;
import com.zyh.fileservice.domain.dto.SignDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhangyuheng
 */
public interface IFileService {
    FileDTO upload(MultipartFile file);

    SignDTO getSign();
}
