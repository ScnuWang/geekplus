package cn.geekview.controller;

import cn.geekview.util.CommonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/index")
public class IndexController {

    @Value("${product.allwebsiteinfo.url}")
    private String allWebsiteInfoUrl;

    @Value("${product.changeinfo.url}")
    private String changeInfoUrl;

    @Value("${product.indexhotproduct.url}")
    private String indexHotProductUrl;

    //首页
    @RequestMapping(value = {"/",""})
    public Object allWebsite(Model model){
        //查询每天的平台数据
        model.addAttribute("websiteinfo",CommonUtils.get(allWebsiteInfoUrl));
        //查询7天内个平台个资金增长情况
        model.addAttribute("changeinfo",CommonUtils.get(changeInfoUrl));
        //首页热门项目
        Map maParm = new HashMap();
        maParm.put("crawlFrequence",60);
        maParm.put("orignalId","20072095");
        model.addAttribute("products",CommonUtils.get(indexHotProductUrl));
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
