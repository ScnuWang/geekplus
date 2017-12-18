package cn.geekview.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class IndexController {

    //首页
    @RequestMapping(value = {"/",""})
    public String index(){
        return "index";
    }

    //登录
    @RequestMapping(value = {"/login"})
    public String login(){
        return "login";
    }

    //注册
    @RequestMapping(value = {"/register"})
    public String register(){
        return "register";
    }
}
