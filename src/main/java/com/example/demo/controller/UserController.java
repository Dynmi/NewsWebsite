package com.example.demo.controller;

import com.example.demo.dao.CollectionDao;
import com.example.demo.dao.NewsDao;
import com.example.demo.dao.UserDao;
import com.example.demo.model.News;
import com.example.demo.model.Collection;
import com.example.demo.model.User;
import com.example.demo.services.CollectionService;
import com.example.demo.services.NewsService;
import com.example.demo.services.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @Autowired
    private NewsDao newsDao;
    @Autowired
    private NewsService newsService;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private CollectionDao collectionDao;


    //进入注册页面，使用Get请求，REST风格的URL能更有雅的处理问题
    @RequestMapping(value = "/register.html", method = RequestMethod.GET)
    public String registerGet() {
        return "register";
    }

    //注册用户，使用POST，传输数据
    @RequestMapping(value = "/register.html", method = RequestMethod.POST)
    public String registerPost(RedirectAttributes  model,
                               //这里和模板中的th:object="${user}"对应起来
                               @ModelAttribute(value = "user") User user,
                               HttpServletRequest request) {
        //使用userService处理业务
        String result = userService.register(user);
        //将结果放入model中，在模板中可以取到model中的值
        if (result.equals("注册成功")) {
            request.getSession().setAttribute("user", user);
            model.addFlashAttribute("result", result);
            return "index";
        } else if (result.equals("该用户名已被使用")) {
            model.addFlashAttribute("result", result);
            return "register";
        }
        return "register";
    }

    @RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public String loginGet() {
        return "login";
    }

    @RequestMapping(value = "/login.html", method = RequestMethod.POST)
    public String loginPost(RedirectAttributes  model,Model m,
                            String password,
                            String username,
                            HttpServletResponse response,
                            HttpServletRequest request) {
        String result = userService.login(username, password);
        User user = userDao.getOneUser(username);
        if (result.equals("登陆成功")) {
            //添加到session中，session是一次会话，在本次会话中都可以取到session中的值
            //若是session中有用户存在则会覆盖原来的user，当session中的user存在时判定用户存在
            request.getSession().setAttribute("user", user);
            model.addFlashAttribute("result", result);
            return "redirect:/index";

        } else if (result.equals("该用户不存在")) {
            m.addAttribute("result", result);
            return "login";
        } else if (result.equals("密码或用户名错误")) {
            m.addAttribute("result", result);
            return "login";
        }
        return response.encodeRedirectURL("/login");
    }

    //登出
    @RequestMapping(value = "/loginOut", method = RequestMethod.GET)
    public String loginOut(RedirectAttributes model, HttpSession session) {
        //从session中删除user属性，用户退出登录
        session.removeAttribute("user");
        model.addFlashAttribute("result", "登出成功！");
        return "redirect:/index";
    }

    //修改密码
    @RequestMapping(value = "/changepassword", method = RequestMethod.POST)
    public String cpPassword(Model model,
                             HttpServletRequest request, String oldpassword, String password, String rpassword) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String username = user.getUsername();
        //使用userService处理业务
        String result = userService.chPassword(username, oldpassword, password, rpassword);
        //将结果放入model中，在模板中可以取到model中的值
        if (result.equals("更改成功！")) {
            session.removeAttribute("user");
            model.addAttribute("result", result);
            return "login";
        } else if (result.equals("用户名输入错误") || result.equals("密码或用户名错误") || result.equals("两次密码不一致")) {
            model.addAttribute("result", result);
            return "extra_profile";
        }
        model.addAttribute("result", result);
        return "extra_profile";
    }


    //修改喜好
    @PostMapping(value = "/setFav")
    public String setFav(Model model, String options, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String username = user.getUsername();
        if (options == null) {
            String result = userService.setfav(username, "1,2,3,4,5");
            model.addAttribute("result", result);
        } else {
            String result = userService.setfav(username, options);
            model.addAttribute("result", result);
        }
        session.removeAttribute("user");
        session.setAttribute("user", user);
        return "extra_profile";


    }
    //查看喜好
    @RequestMapping("/page_todo.html")
    public String collection(HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Integer id=user.getU_id();
        PageInfo<Collection> pageInfo = collectionService.findCollectionuid(1,10,id);
        List<Collection> collections= pageInfo.getList();
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("collection", collections);
        return "page_todo";
    }
    @RequestMapping("/page_todo/list")
    public String collectionPage(HttpServletRequest request,Model model
    ,@RequestParam(defaultValue = "1")int pageNum){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Integer id=user.getU_id();
        PageInfo<Collection> pageInfo = collectionService.findCollectionuid(pageNum,10,id);
        List<Collection> collections= pageInfo.getList();
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("collection", collections);
        return "page_todo";
    }
    //删除喜好
    @DeleteMapping("/collectionmanage/{c_id}")//删除用户
    public String deletecollect(@PathVariable("c_id") Integer c_id, RedirectAttributes model) {
        if (c_id != null) {
            collectionDao.deleteById(c_id);
            model.addFlashAttribute("result", "删除成功！");
        } else {
            model.addFlashAttribute("result", "收藏已不存在！");
        }
        return "redirect:/page_todo.html";
    }

    //管理模块
    @RequestMapping(value = {"/admincenter.html", "/admincenter"})
    public String admincenter(Model m) {
        PageInfo<User> pageInfo = userService.findPage(1,10);//将用户信息送往前端
        List<User> users= pageInfo.getList();
        m.addAttribute("pageInfo",pageInfo);
        m.addAttribute("users", users);
        return "admincenter";
    }

    @RequestMapping(value = {"/admincenter/list"})
    public String userpage(Model m,
                           @RequestParam(defaultValue = "1")int pageNum) {
        PageInfo<User> pageInfo = userService.findPage(pageNum,10);//将用户信息送往前端
        List<User> users= pageInfo.getList();
        m.addAttribute("pageInfo",pageInfo);
        m.addAttribute("users", users);
        return "admincenter";
    }

    @DeleteMapping("/usermanage/{username}")//删除用户
    public String deleteUser(@PathVariable("username") String username, RedirectAttributes model) {
        if (username != null) {
            userDao.deleteByName(username);
            model.addFlashAttribute("result", "删除成功！");
        } else {
            model.addFlashAttribute("result", "用户名不存在！");
        }
        return "redirect:/admincenter";
    }

    @PutMapping(value = "/namemanage/{username}")
    public String EditUser(@PathVariable(value = "username") String oldusername, String username, RedirectAttributes model) {
        if (oldusername != null && username != null) {
            userDao.updateUsername(oldusername, username);
            model.addFlashAttribute("result", "更改成功！");
        } else {
            model.addFlashAttribute("result", "用户名为空！");
        }

        return "redirect:/admincenter";
    }

    @RequestMapping(value = {"/newsmanage.html", "/newsmanage","/admincenter/newsmanage.html"})
    public String newsmanage(Model m) {
        PageInfo<News> pageInfo = newsService.findPage(1,10);//将新闻信息送往前端
        List<News> news= pageInfo.getList();
        m.addAttribute("pageInfo",pageInfo);
        m.addAttribute("news", news);
        return "newsmanage";
    }
    @RequestMapping(value = {"/newsmanage/list"})
    public String newsrpage(Model m,
                           @RequestParam(defaultValue = "1")int pageNum) {
        PageInfo<News> pageInfo = newsService.findPage(pageNum,10);//将用户信息送往前端
        List<News> news= pageInfo.getList();
        m.addAttribute("pageInfo",pageInfo);
        m.addAttribute("news", news);
        return "newsmanage";
    }


    //新闻发布
    @RequestMapping(value = {"/news_publish","/news_publish.html"})
    public String newspub () {
        return "news_publish";
    }
    @PostMapping(value = "/publishnews")
    public String NewsPublish(Model model,
                              String title, String contents, String source, String category, String time) {
       if(title == null || title=="" || contents == null ||contents =="" ||source ==""||source==null||time==null||time=="")
        {
            model.addAttribute("result","填入内容不能为空！");
                    return "news_publish";
        }
        else
        {
            String result = newsService.publish(title,contents,source,category,time);
            model.addAttribute("result",result);
            return "news_publish";
        }

    }

    @DeleteMapping("/newsdelete/{n_id}")//删除新闻
    public String deleteNews(@PathVariable("n_id") Integer n_id, Model model) {
        if (n_id != null) {
            newsDao.deleteById(n_id);
            model.addAttribute("result", "删除成功！");
        } else {
            model.addAttribute("result", "新闻不存在！");
        }
        return "redirect:/newsmanage";
    }

    @RequestMapping("/newsedit/{n_id}")
    public String newsedit (@PathVariable("n_id") Integer n_id, Model model) {
        News editnews=newsDao.getOneNewsById(n_id);
        model.addAttribute("editnews",editnews);
        model.addAttribute("n_id",n_id);
        return "news_edit";
    }

    @PostMapping(value = "/editnews")
    public String NewsEdit(RedirectAttributes model,int newsid,
                              String title, String contents, String source, String category, String time) {
        if (title == null || title == "" || contents == null || contents == "" || source == "" || source == null || time == null || time == "") {
            model.addFlashAttribute("result", "填入内容不能为空！");
            return "redirect:/newsmanage";
        } else {
            String result = newsService.Edit(title, contents, source, category, time, newsid);
            model.addFlashAttribute("result", result);
            return "redirect:/newsmanage";
        }

    }
    @RequestMapping("/extra_profile.html")
    public String profile(){
        return "extra_profile";
    }
}
