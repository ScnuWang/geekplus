package cn.geekview.service.impl;

import cn.geekview.entity.mapper.TdreamUserMapper;
import cn.geekview.entity.model.TdreamUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserServiceImpl")
public class UserServiceImpl {
    @Autowired
    private TdreamUserMapper userMapper;

    /**
     * 注册添加用户
     */
    public int addUser(TdreamUser user){
        return userMapper.insert(user);
    }

}
