package cn.geekview.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailServiceImpl {
    protected Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.from.addr}")
    private String from;


    /**
     * 发送普通文本邮件
     * @param to  接受邮件的邮箱地址
     * @param subject 邮件的主题
     * @param content 邮件的内容
     */
    public void sendSimpleMail(String to,String subject,String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        try {
            mailSender.send(message);
            logger.info("发送邮件主题为:-----"+subject+"-----成功！");
        }catch (Exception e){
            logger.info("发送邮件失败："+e.getMessage());
        }
    }


    /**
     *  发送多媒体邮件
     * @param to
     * @param subject
     * @param content
     */
    public void sendHtmlMail(String to,String subject,String content){
        MimeMessage message = mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
            logger.info("html邮件发送成功");
        } catch (MessagingException e) {
            logger.error("发送html邮件时发生异常！", e);
        }
    }




}
