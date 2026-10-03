package com.zyh.commondomain.constants;

/**
 * @author zhangyuheng
 */
public class MessageConstants {
    /**
     * 发送邮箱成功的响应码
     */
    public static final String MAIL_MSG_OK = "OK";

    /**
     * 发送次数的key
     */
    public static final String MAIL_CODE_TIMES_KEY = "mail:times:";

    /**
     * 邮箱验证码频繁发送的key
     */
    public static final String MAIL_CODE_KEY = "mail:code:";

    /**
     * 默认验证码的长度
     */
    public static final int DEFAULT_MAIL_LENGTH = 6;

    /**
     * 默认验证码
     */
    public static final String DEFAULT_MAIL_CODE = "123456";
}
