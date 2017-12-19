package cn.geekview.service.impl;

import cn.geekview.entity.mapper.TdreamUserMapper;
import cn.geekview.entity.model.TdreamReturnObject;
import cn.geekview.entity.model.TdreamUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserServiceImpl")
public class UserServiceImpl {

    @Autowired
    private TdreamUserMapper userMapper;

    @Autowired
    private TdreamReturnObject returnObject;

    /**
     * 注册添加用户
     */
    public TdreamReturnObject addUser(TdreamUser user){
        String email = userMapper.emailIsExist(user);
        if (email==null){
            int i = userMapper.insert(user);
            if (i==1){
                returnObject.setReturnCode("0000");
                returnObject.setReturnMessage("用户注册成功");
                returnObject.setReturnObject(email);
            }
        }else {
            returnObject.setReturnCode("9999");
            returnObject.setReturnMessage("该邮箱已经注册");
        }
        return returnObject;
    }

    /**
     * 判断用户是否存在
     */
    public TdreamReturnObject queryUser(TdreamUser user){
        String email = userMapper.queryByEamilAndPassword(user);
        if (email!=null){
            returnObject.setReturnCode("0000");
            returnObject.setReturnMessage("用户名和密码正确");
            returnObject.setReturnObject(email);
        }else {
            returnObject.setReturnCode("9999");
            returnObject.setReturnMessage("用户名或密码不正确");
        }
        return returnObject;
    }

}
