package com.zyh.commonmessage.service;

import jakarta.annotation.Resource;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * @author zhangyuheng
 */
@Slf4j
@Component
public class MailService {

    /**
     * 官方名称（邮箱地址）
     */
    @Value(value = "${spring.mail.username:}")
    private String from;

    @Resource
    private JavaMailSender mailSender;

    /**
     * 发送 HTML 邮件
     *
     * @param to      目标邮箱地址
     * @param subject 标题
     * @param context 正文（HTML 格式）
     * @return 是否发送成功
     */
    public Boolean sendMessage(String to, String subject, String context) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            // 第二个参数 true 表示创建 multipart 消息，支持 HTML
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            // 第二个参数 true 表示正文是 HTML，会被渲染而不是原样显示
            helper.setText(context, true);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("向{}发送邮件失败！", to, e);
            return false;
        }
        return true;
    }
}