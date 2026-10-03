package com.zyh.fileservice.domain.vo;

import lombok.Data;

/**
 * @author zhangyuheng
 */
@Data
public class SignVO {
    /**
     * 签名
     */
    private String signature;

    private String host;

    private String pathPrefix;

    private String xOSSCredential;

    private String xOSSDate;

    private String policy;
}
