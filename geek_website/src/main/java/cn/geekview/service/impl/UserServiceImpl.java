package cn.geekview.service.impl;

import cn.geekview.entity.mapper.TdreamUserMapper;
import cn.geekview.entity.model.TdreamReturnObject;
import cn.geekview.entity.model.TdreamUser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserServiceImpl")
public class UserServiceImpl {

    protected Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private TdreamUserMapper userMapper;

    @Autowired
    private TdreamReturnObject returnObject;

    /**
     * 注册添加用户
     */
    public TdreamReturnObject addUser(TdreamUser user){
        try {
            String email = userMapper.emailIsExist(user);
            if (email==null){
                userMapper.insert(user);
                returnObject.setReturnCode("1001");
                returnObject.setReturnMessage("注册成功");
                returnObject.setReturnObject(email);
            }else {
                returnObject.setReturnCode("1002");
                returnObject.setReturnMessage("该邮箱已经注册");
            }
        }catch (Exception e){
            logger.error(e);
            returnObject.setReturnCode("9999");
            returnObject.setReturnMessage("添加用户出现异常");
        }
        return returnObject;
    }

    /**
     * 判断用户是否存在
     */
    public TdreamReturnObject queryUser(TdreamUser user){
        try {
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
            returnObject.setReturnMessage("用户登录出现异常");
        }
        return returnObject;
    }

    public TdreamReturnObject active_eamil(TdreamUser user){
        try {
            userMapper.active_email(user);
            returnObject.setReturnCode("1005");
            returnObject.setReturnMessage("邮箱激活成功");
        }catch (Exception e){
            logger.error(e);
            returnObject.setReturnCode("9999");
            returnObject.setReturnMessage("邮箱激活失败");
        }
        return returnObject;

    }

}
