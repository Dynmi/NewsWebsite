package com.example.controller;

import com.example.model.User;
import com.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;


/**
 * Created by Shusheng Shi on 2017/5/8.-1
 */
@Controller
public class UserController {
    //自动注入userService，用来处理业务
    @Autowired
    private UserService userService;

    //跳转链接，跳转到主页
    @RequestMapping("")
    public String index(HttpServletResponse response) {
        //重定向到 /index
        return response.encodeRedirectURL("/index");
    }

    @RequestMapping("/index")
    public String home(Model model) {
        //对应到templates文件夹下面的index
        return "index";
    }

    //进入注册页面，使用Get请求，REST风格的URL能更有雅的处理问题
    @RequestMapping(value = "/register.html", method = RequestMethod.GET)
    public String registerGet() {
        return "register.html";
    }

    //注册用户，使用POST，传输数据
    @RequestMapping(value = "/register.html", method = RequestMethod.POST)
    @ResponseBody
    public String registerPost(Model model,
                               //这里和模板中的th:object="${user}"对应起来
                               @ModelAttribute(value = "user") User user,
                               HttpServletResponse response) {
        //使用userService处理业务
        String result = userService.register(user);
        //将结果放入model中，在模板中可以取到model中的值
        if (result.equals("注册成功")) {
            model.addAttribute("result", result);
            return "index";
        }
        else if (result.equals("该用户名已被使用")) {
            model.addAttribute("result", result);
            return "register";
        }
        return "register";
    }

    @RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public String loginGet() {
        return "login";
    }

    @RequestMapping(value = "/login.html", method = RequestMethod.POST)
    public String loginPost(Model model,
                            @ModelAttribute(value = "user") User user,
                            HttpServletResponse response,
                            HttpSession session) {
        String result = userService.login(user);
        if (result.equals("登陆成功")) {
            //添加到session中，session是一次会话，在本次会话中都可以取到session中的值
            //若是session中有用户存在则会覆盖原来的user，当session中的user存在时判定用户存在
            session.setAttribute("user",user);
            model.addAttribute("result", result);
            return "index";

        }
        else if(result.equals("该用户不存在"))
        {
            model.addAttribute("result", result);
            return "login";
        }
        else if(result.equals("密码或用户名错误"))
        {
            model.addAttribute("result", result);
            return "login";
        }
        return response.encodeRedirectURL("/login");
    }

    @RequestMapping(value = "/loginOut", method = RequestMethod.GET)
    public String loginOut(HttpSession session) {
        //从session中删除user属性，用户退出登录
        session.removeAttribute("user");
        return "index";
    }
}

