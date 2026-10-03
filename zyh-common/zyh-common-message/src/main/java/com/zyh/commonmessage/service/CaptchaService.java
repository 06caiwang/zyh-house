package com.zyh.commonmessage.service;

import com.zyh.commoncore.utils.VerifyUtil;
import com.zyh.commondomain.constants.MessageConstants;
import com.zyh.commondomain.domain.ResultCode;
import com.zyh.commondomain.exception.ServiceException;
import com.zyh.commonredis.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangyuheng
 * 验证码服务
 */

@Component
public class CaptchaService {
    /**
     * redis服务类
     */
    @Autowired
    private RedisService redisService;

    /**
     *  每日发送验证码次数的限制
     */
    @Value("${zyh.send-limit:3}")
    private Integer sendLimit;

    /**
     * 验证码的有效期，单位是分钟
     */
    @Value("${zyh.code-expiration:2}")
    private Long mailCodeExpiration;

    /**
     * 用来判断是否发送随机验证码
     */
    @Value("${zyh.send-message:false}")
    private boolean sendMessage;


    @Autowired
    private MailService mailService;

    /**
     * 发送验证码
     * @param mail 邮箱
     * @return 验证码
     */
    public String sendCode(String mail) {
        // 1. 校验是否超过每日的发送限制（针对每个邮箱）
        String limitCacheKey = MessageConstants.MAIL_CODE_TIMES_KEY + mail;
        Integer times = redisService.getCacheObject(limitCacheKey, Integer.class);
        times = times == null ? 0 : times;
        if (times >= sendLimit) {
            throw new ServiceException(ResultCode.SEND_MSG_OVERLIMIT);
        }

        // 2. 校验是否在1分钟内频繁发送
        String codeKey = MessageConstants.MAIL_CODE_KEY + mail;
        String cacheValue = redisService.getCacheObject(codeKey, String.class);
        long expireTime =  redisService.getExpire(codeKey);
        if (!StringUtils.isEmpty(cacheValue) && expireTime > mailCodeExpiration * 60 - 60) {
            long time = expireTime - mailCodeExpiration * 60 + 60;
            throw new ServiceException("操作频繁，请在"+ time+ "秒之后再试", ResultCode.INVALID_PARA.getCode());
        }

        // 3. 生成验证码
        String verifyCode = sendMessage
                ? VerifyUtil.generateVerifyCode(MessageConstants.DEFAULT_MAIL_LENGTH)
                : MessageConstants.DEFAULT_MAIL_CODE;

        // 4. 发送线上邮件
        if (sendMessage) {
            boolean result = send(mail, verifyCode);
            if (!result) {
                throw new ServiceException(ResultCode.MAIL_MSG_FAILED);
            }
        }

        // 5. 设置验证码的缓存
        redisService.setCacheObject(codeKey, verifyCode, mailCodeExpiration, TimeUnit.MINUTES);

        // 6. 设置发送次数限制的缓存 （无法预先设置缓存，只能先读后写）
        long seconds = ChronoUnit.SECONDS.between(LocalDateTime.now(),
                LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0));
        redisService.setCacheObject(limitCacheKey, times + 1, seconds, TimeUnit.SECONDS);

        return verifyCode;
    }

    /**
     * 发送邮箱验证码
     * @param to 目标用户邮箱
     * @return 是否发送成功
     */
    private boolean send(String to, String code) {
        String subject = "脚手架服务登录验证码";
        String context = "<div style=\"max-width:480px;margin:0 auto;padding:24px;"
                + "font-family:'Microsoft YaHei',Arial,sans-serif;color:#333;\">"
                + "<h2 style=\"color:#2c3e50;margin:0 0 16px;\">登录验证码</h2>"
                + "<p style=\"font-size:14px;line-height:1.6;\">您好，您正在尝试登录脚手架服务，本次登录验证码为：</p>"
                + "<div style=\"text-align:center;margin:24px 0;\">"
                + "<span style=\"display:inline-block;padding:12px 32px;font-size:32px;"
                + "font-weight:bold;letter-spacing:8px;color:#1890ff;"
                + "background:#f0f7ff;border-radius:8px;\">" + code + "</span>"
                + "</div>"
                + "<p style=\"font-size:14px;line-height:1.6;\">验证码 <strong>" + mailCodeExpiration
                + "分钟</strong> 内有效，请勿泄露给他人。</p>"
                + "<p style=\"font-size:12px;color:#999;margin-top:24px;\">如非本人操作，请忽略本邮件。</p>"
                + "</div>";

        return mailService.sendMessage(to, subject, context);
    }

    /**
     * 从缓存中获取手机号的验证码
     * @param mail 手机号
     * @return 验证码
     */
    public String getCode(String mail) {
        String cacheKey = MessageConstants.MAIL_CODE_KEY + mail;
        return redisService.getCacheObject(cacheKey, String.class);
    }

    /**
     * 从缓存中删除邮箱的验证码
     * @param mail 邮箱
     * @return 验证码
     */
    public boolean deleteCode(String mail) {
        String cacheKey = MessageConstants.MAIL_CODE_KEY + mail;
        return redisService.deleteObject(cacheKey);
    }

    /**
     * 校验邮箱与验证码是否匹配
     * @param mail 邮箱
     * @param code 验证码
     * @return 布尔类型
     */
    public boolean checkCode(String mail, String code) {
        if (getCode(mail) == null || StringUtils.isEmpty(getCode(mail))) {
            throw new ServiceException(ResultCode.INVALID_CODE);
        }
        return getCode(mail).equals(code);
    }
}
