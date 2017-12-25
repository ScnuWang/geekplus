package cn.geekview.service.impl;

import cn.geekview.entity.mapper.TdreamUserMapper;
import cn.geekview.entity.model.TdreamReturnObject;
import cn.geekview.entity.model.TdreamUser;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service("UserServiceImpl")
public class UserServiceImpl {

    protected Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private TdreamUserMapper userMapper;

    @Autowired
    private TdreamReturnObject returnObject;

    @Value("${email.certfication.url}")
    private String email_certfication_url;

    @Autowired
    private MailServiceImpl mailService;

    /**
     * 注册添加用户
     */
    public TdreamReturnObject addUser(TdreamUser user){
        String email = user.getEmail();
        String password = user.getPassword();
        user.setNickName(email.split("@")[0]);//截取邮箱前面的字符串作为用户名，后续提供修改用户名功能

        //对用户密码进行加密MD5处理
        user.setPassword(DigestUtils.md5Hex(password));

        //默认注册未激活
        user.setUserStatus(0);
        Date registerDate = new Date();
        user.setRegisterTime(registerDate);
        //邮箱激活码
        String activeCode = UUID.randomUUID().toString();
        user.setActiveCode(activeCode);
        //设置激活有效时间为2小时
        user.setExprieTime(new DateTime(registerDate).plusHours(2).toDate());
        try {
            String result = userMapper.emailIsExist(user);
            if (result==null){
                userMapper.insert(user);
                returnObject.setReturnCode("1001");
                returnObject.setReturnMessage("注册成功");
                returnObject.setReturnObject(result);
            }else {
                returnObject.setReturnCode("1002");
                returnObject.setReturnMessage("该邮箱已经注册");
            }
        }catch (Exception e){
            logger.error(e);
            returnObject.setReturnCode("9999");
            returnObject.setReturnMessage("用户注册异常");
        }

        //发送邮件
        String email_cert_url = email_certfication_url+email+"&activeCode="+activeCode;
        mailService.sendHtmlMail(email,"极客视界--邮箱激活","激活邮箱请点击：<a href='"+email_cert_url+"'>邮箱激活</a>");

        return returnObject;
    }

    /**
     * 判断用户是否存在
     */
    public TdreamReturnObject queryUser(TdreamUser user){
        try {
            //用户密码加密
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
            String email = userMapper.queryByEamilAndPassword(user);
            if (email!=null){
                returnObject.setReturnCode("1003");
                returnObject.setReturnMessage("登录成功");
                returnObject.setReturnObject(email);
            }else {
                returnObject.setReturnCode("1004");
                returnObject.setReturnMessage("登录失败，用户名或密码错误");
            }
        }catch (Exception e){
            logger.error(e);
            returnObject.setReturnCode("9999");
            returnObject.setReturnMessage("用户登录异常");
        }
        return returnObject;
    }

    public TdreamReturnObject active_eamil(TdreamUser user){
        try {
            //判断激活的邮箱是否存在，已经是否已经超过激活时间，超过激活时间的删除注册记录，重新注册
            String result = userMapper.queryByEmailAndActiveCode(user);
            if (result!=null){
                user.setUserStatus(1);
                userMapper.active_email(user);
                returnObject.setReturnCode("1005");
                returnObject.setReturnMessage("邮箱激活成功");
            }else {
                userMapper.deleteByEmailAndActiveCode(user);
                returnObject.setReturnCode("1006");
                returnObject.setReturnMessage("邮箱激活失败");
            }
        }catch (Exception e){
            logger.error(e);
            returnObject.setReturnCode("9999");
            returnObject.setReturnMessage("邮箱激活异常");
        }
        return returnObject;
    }

}
