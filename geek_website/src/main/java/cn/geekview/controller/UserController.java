package cn.geekview.controller;

import cn.geekview.entity.model.TdreamUser;
import cn.geekview.service.impl.UserServiceImpl;
import cn.geekview.util.CommonUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = {"/register"})
    public String login(TdreamUser user){
        if (!CommonUtils.isMailAddress(user.getEmail())){
            //调回回，并提示不是邮箱格式
            return "register";
        }
        user.setNickName(user.getEmail().split("@")[0]);//截取邮箱前面的字符串作为用户名，后续提供修改用户名功能
        user.setUserStatus(1);
        user.setRegisterTime(new Date());
        int i = userService.addUser(user);
        if (i==1){
            return "index";
        }else {
            return "error";
        }
    }

}
