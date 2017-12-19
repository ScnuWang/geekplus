package cn.geekview.controller;

import cn.geekview.entity.model.TdreamReturnObject;
import cn.geekview.entity.model.TdreamUser;
import cn.geekview.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = {"/register"})
    public String register(TdreamUser user, Model model){
        //判断注册邮箱、邮箱格式和密码的长度
        user.setNickName(user.getEmail().split("@")[0]);//截取邮箱前面的字符串作为用户名，后续提供修改用户名功能
        user.setUserStatus(1);
        user.setRegisterTime(new Date());
        TdreamReturnObject returnObject = userService.addUser(user);
        model.addAttribute("email",returnObject.getReturnObject());
        if ("0000".equals(returnObject.getReturnCode())){
            return "index";
        }else if ("9999".equals(returnObject.getReturnCode())){
            return "login";
        }else {
            return "error";
        }
    }

    @RequestMapping(value = {"/login"})
    public String login(TdreamUser user, Model model){
        TdreamReturnObject returnObject = userService.queryUser(user);
        model.addAttribute("email",returnObject.getReturnObject());
        if ("0000".equals(returnObject.getReturnCode())){
            return "index";
        }else if ("9999".equals(returnObject.getReturnCode())){
            return "login";
        }else {
            return "error";
        }
    }

}
