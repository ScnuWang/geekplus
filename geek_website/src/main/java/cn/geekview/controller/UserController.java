package cn.geekview.controller;

import cn.geekview.entity.model.TdreamReturnObject;
import cn.geekview.entity.model.TdreamUser;
import cn.geekview.service.impl.UserServiceImpl;
import cn.geekview.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * 用户注册
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value = {"/register"},method = RequestMethod.POST)
    public String register(TdreamUser user, Model model){

        String email = user.getEmail();
        String password = user.getPassword();
        //判断关键字段是否为空
        if (StringUtils.isEmpty(email)||StringUtils.isEmpty(password)){
            return "user/login";
        }

        //判断注册邮箱、邮箱格式和密码的格式、长度
        if (!CommonUtils.checkEmail(email)||!CommonUtils.checkPassword(password)){
            return "user/login";
        }

        //添加记录到数据库
        TdreamReturnObject returnObject = userService.addUser(user);
        //页面跳转
        model.addAttribute("email",returnObject.getReturnObject());
        model.addAttribute("user_status",0);
        if ("1001".equals(returnObject.getReturnCode())){
            //插入成功页面跳转至首页
            return "index";
        }else if ("1002".equals(returnObject.getReturnCode())){
            //已经注册跳转到登录页面
            return "user/login";
        }else {
            //注册异常跳转到异常页面,或者重新注册
            return "error";
        }
    }

    /**
     * 用户登录
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value = {"/login"},method = RequestMethod.POST)
    public String login(TdreamUser user, Model model){

        //查询用户
        TdreamReturnObject returnObject = userService.queryUser(user);

        //如果没有激活，要提示激活

        model.addAttribute("email",returnObject.getReturnObject());
        if ("1003".equals(returnObject.getReturnCode())){
            //登录成功页面跳转至首页
            return "index";
        }else if ("1004".equals(returnObject.getReturnCode())){
            //登录成功页面跳转至登录页
            return "user/login";
        }else {
            //注册异常跳转到异常页面,或者重新登录
            return "error";
        }
    }

    /**
     *  邮箱验证
     * @return
     */
    @RequestMapping(value = {"/email_certfication"},method = RequestMethod.GET)
    public String email_certfication(TdreamUser user){
        TdreamReturnObject returnObject = userService.active_eamil(user);
        if ("1005".equals(returnObject.getReturnCode())){
            //邮箱激活成功
            return "index";
        }else {
            //邮箱激活失败
            return "error";
        }
    }

}
