package com.example.demo.controller;

import com.example.demo.dao.UserDao;
import com.example.demo.model.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class UserController {
    //自动注入userService，用来处理业务
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;

    //跳转链接，跳转到主页
    @RequestMapping({"/","/index","/index.html"})
    public String index(HttpServletResponse response,
                         HttpServletRequest request) {
        HttpSession session = request.getSession();
        //重定向到 /index
        return response.encodeRedirectURL("/index");
    }

    //进入注册页面，使用Get请求，REST风格的URL能更有雅的处理问题
    @RequestMapping(value = "/register.html", method = RequestMethod.GET)
    public String registerGet() {
        return "register";
    }

    //注册用户，使用POST，传输数据
    @RequestMapping(value = "/register.html", method = RequestMethod.POST)
    public String registerPost(Model model,
                               //这里和模板中的th:object="${user}"对应起来
                               @ModelAttribute(value = "user") User user,
                               HttpServletRequest request) {
        //使用userService处理业务
        String result = userService.register(user);
        //将结果放入model中，在模板中可以取到model中的值
        if (result.equals("注册成功")) {
            request.getSession().setAttribute("user",user);
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
                            String password,
                            String username,
                            HttpServletResponse response,
                            HttpServletRequest request) {
        String result = userService.login(username, password);
        User user = userDao.getOneUser(username);
        if (result.equals("登陆成功")) {
            //添加到session中，session是一次会话，在本次会话中都可以取到session中的值
            //若是session中有用户存在则会覆盖原来的user，当session中的user存在时判定用户存在
            request.getSession().setAttribute("user",user);
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
    public String loginOut(Model model,HttpSession session) {
        //从session中删除user属性，用户退出登录
        session.removeAttribute("user");
        model.addAttribute("result", "登出成功！");
        return "index";
    }

    @RequestMapping(value = "/changepassword", method = RequestMethod.POST)
    public String cpPassword(Model model,
                             HttpServletRequest request,String oldpassword, String password, String rpassword) {
        HttpSession session= request.getSession();
        User user = (User)session.getAttribute("user");
        String username= user.getUsername();
        //使用userService处理业务
        String result = userService.chPassword(username,oldpassword,password,rpassword);
        //将结果放入model中，在模板中可以取到model中的值
        if (result.equals("更改成功！")) {
            session.removeAttribute("user");
            model.addAttribute("result", result);
            return "login";
        }
        else if (result.equals("用户名输入错误") || result.equals("密码或用户名错误") || result.equals("两次密码不一致")) {
            model.addAttribute("result", result);
            return "extra_profile";
        }
        model.addAttribute("result", result);
        return "extra_profile";
    }
    @RequestMapping(value = {"/admincenter.html","/admincenter"})
    public String admincenter(Model m) {
        List<User> users = userDao.findAll();//将用户信息送往前端
        m.addAttribute("users",users);
        return "admincenter";
    }
    @DeleteMapping("/usermanage/{username}")//删除用户
    public String deleteUser(@PathVariable("username") String username,Model model) {
        if(username != null)
        {
            userDao.deleteByName(username);
            model.addAttribute("result", "删除成功！");
        }
        else
        {
            model.addAttribute("result", "用户名不存在！");
        }
        return "redirect:/admincenter";
    }
    @PutMapping(value="/namemanage/{username}")
    public String EditUser(@PathVariable(value="username") String oldusername,String username,Model model)
    {
        if(oldusername!=null && username!=null)
        {
            userDao.updateUsername(oldusername,username);
            model.addAttribute("result","更改成功！");
        }
        else
        {
            model.addAttribute("result","用户名为空！");
        }

        return "redirect:/admincenter";
    }



}

