package com.example.demo.controller;

import com.example.demo.dao.CollectionDao;
import com.example.demo.dao.NewsDao;
import com.example.demo.dao.UserDao;
import com.example.demo.model.Comment;
import com.example.demo.model.News;
import com.example.demo.model.User;
import com.example.demo.services.CommentService;
import com.example.demo.services.NewsService;
import com.example.demo.services.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class NewsController {
    @Autowired
    CommentService CommentService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private NewsDao newsDao;
    @Autowired
    private NewsService newsService;
    @Autowired
    private CollectionDao collectionDao;
    //跳转链接，跳转到主页
    @RequestMapping({"/index", "","/index.html"})
    public String index(HttpServletRequest request,Model model) {
        HttpSession session = request.getSession();
        if(session.getAttribute("user")!=null)
        {
            User user = (User) session.getAttribute("user");
            String username=user.getUsername();
            String favor = userDao.getOneUserFav(username);
            List<News> news= newsService.favornews(favor);
            model.addAttribute("news",news);
        }
        else
        {
            List<News> news= newsService.favornews("1,2,3,4,5");
            model.addAttribute("news",news);
        }
        return "index";
    }
    @RequestMapping("/musenews.html")
    public String muse(Model m){
        PageInfo<News> pageInfo = newsService.findPageCate(1,10,"娱乐新闻");//将新闻信息送往前端
        List<News> news= pageInfo.getList();
        m.addAttribute("pageInfo",pageInfo);
        m.addAttribute("news", news);
        return "musenews";
    }
    @RequestMapping(value = {"/musenews/list"})
    public String musepage(Model m,
                            @RequestParam(defaultValue = "1")int pageNum) {
        PageInfo<News> pageInfo = newsService.findPageCate(pageNum,10,"娱乐新闻");//将新闻信息送往前端
        List<News> news= pageInfo.getList();
        m.addAttribute("pageInfo",pageInfo);
        m.addAttribute("news", news);
        return "musenews";
    }
    @RequestMapping("/financenews.html")
    public String finance(Model m){
        PageInfo<News> pageInfo = newsService.findPageCate(1,10,"财经新闻");//将新闻信息送往前端
        List<News> news= pageInfo.getList();
        m.addAttribute("pageInfo",pageInfo);
        m.addAttribute("news", news);
        return "financenews";
    }
    @RequestMapping(value = {"/financenews/list"})
    public String financepage(Model m,
                           @RequestParam(defaultValue = "1")int pageNum) {
        PageInfo<News> pageInfo = newsService.findPageCate(pageNum,10,"财经新闻");//将新闻信息送往前端
        List<News> news= pageInfo.getList();
        m.addAttribute("pageInfo",pageInfo);
        m.addAttribute("news", news);
        return "financenews";
    }
    @RequestMapping("/sportsnews.html")
    public String sport(Model m){
        PageInfo<News> pageInfo = newsService.findPageCate(1,10,"体育新闻");//将新闻信息送往前端
        List<News> news= pageInfo.getList();
        m.addAttribute("pageInfo",pageInfo);
        m.addAttribute("news", news);
        return "sportsnews";
    }
    @RequestMapping(value = {"/sportsnews/list"})
    public String sportspage(Model m,
                              @RequestParam(defaultValue = "1")int pageNum) {
        PageInfo<News> pageInfo = newsService.findPageCate(pageNum,10,"体育新闻");//将新闻信息送往前端
        List<News> news= pageInfo.getList();
        m.addAttribute("pageInfo",pageInfo);
        m.addAttribute("news", news);
        return "sportsnews";
    }

    @RequestMapping("/technews.html")
    public String tech(Model m){
        PageInfo<News> pageInfo = newsService.findPageCate(1,10,"科技新闻");//将新闻信息送往前端
        List<News> news= pageInfo.getList();
        m.addAttribute("pageInfo",pageInfo);
        m.addAttribute("news", news);
        return "technews";
    }
    @RequestMapping(value = {"/technews/list"})
    public String techpage(Model m,
                              @RequestParam(defaultValue = "1")int pageNum) {
        PageInfo<News> pageInfo = newsService.findPageCate(pageNum,10,"科技新闻");//将新闻信息送往前端
        List<News> news= pageInfo.getList();
        m.addAttribute("pageInfo",pageInfo);
        m.addAttribute("news", news);
        return "technews";
    }
    @RequestMapping("/militarynews.html")
    public String military(Model m){
        PageInfo<News> pageInfo = newsService.findPageCate(1,10,"军事新闻");//将新闻信息送往前端
        List<News> news= pageInfo.getList();
        m.addAttribute("pageInfo",pageInfo);
        m.addAttribute("news", news);
        return "militarynews";
    }
    @RequestMapping(value = {"/militarynews/list"})
    public String militarypage(Model m,
                              @RequestParam(defaultValue = "1")int pageNum) {
        PageInfo<News> pageInfo = newsService.findPageCate(pageNum,10,"军事新闻");//将新闻信息送往前端
        List<News> news= pageInfo.getList();
        m.addAttribute("pageInfo",pageInfo);
        m.addAttribute("news", news);
        return "militarynews";
    }
    @RequestMapping("/newsitem/{n_id}")
    public String newsedit (@PathVariable("n_id") Integer n_id, @RequestParam(defaultValue = "1")int pageNum,Model model) {
        PageInfo<Comment> pageInfo = CommentService.findPageNid(pageNum, 10, n_id);
        List<Comment> comment = pageInfo.getList();
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("comment", comment);
        News news = newsDao.getOneNewsById(n_id);
        model.addAttribute("news", news);
        model.addAttribute("n_id", n_id);
        return "page_news_item";
    }
    @PostMapping(value = "/searchnews")
    public String commentPublish(Model model, String newsname
    ) {
        List<News> news=newsDao.searchnews(newsname);
        model.addAttribute("news",news);
        model.addAttribute("newsname",newsname);
        return "extra_search";
    }
    @PostMapping(value = "/collectnews")
    public String collectionadd(RedirectAttributes model, Integer u_id, Integer n_id, String title
    ) {
        if (u_id == null) {
            model.addFlashAttribute("result", "请登录后再使用收藏！");
            return "redirect:/newsitem/" + n_id;
        } else {
            model.addFlashAttribute("result", "收藏成功");
            collectionDao.addcollection(u_id,title,n_id);
            return "redirect:/newsitem/" + n_id;
        }
    }


    
}
