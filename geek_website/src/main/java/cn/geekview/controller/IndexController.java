package cn.geekview.controller;

import cn.geekview.util.CommonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class IndexController {

    @Value("${product.allwebsiteinfo.url}")
    private String allWebsiteInfoUrl;

    //首页
    @RequestMapping(value = {"/",""})
    public Object allWebsite(Model model){
        //查询每天的平台数据
        model.addAttribute("websiteinfo",CommonUtils.get(allWebsiteInfoUrl));
        return "index";
    }

    //登录
    @RequestMapping(value = {"/login"})
    public String login(){
        return "user/login";
    }

    //注册
    @RequestMapping(value = {"/register"})
    public String register(){
        return "user/register";
    }
}
