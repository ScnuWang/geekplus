package cn.geekview.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailServiceImpl {
    protected Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.from.addr}")
    private String from;

    @Value("${mail.to.addr}")
    private String to;

    public void sendMail(String subject,String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        try {
            mailSender.send(message);
            logger.info("发送邮件成功！");
        }catch (Exception e){
            logger.info("发送邮件失败："+e.getMessage());
        }
    }

}
