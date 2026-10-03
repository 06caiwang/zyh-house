package com.zyh.fileservice.domain.dto;

import lombok.Data;

/**
 * @author zhangyuheng
 */
@Data
public class FileDTO {
    private String url;

    //路径信息   /目录/文件名.后缀名
    private String key;

    private String name;
}
