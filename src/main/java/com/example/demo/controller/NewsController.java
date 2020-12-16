package com.example.demo.controller;

import com.example.demo.dao.*;
import com.example.demo.model.*;
import com.example.demo.services.*;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private CollectionService collectionservice;
    @Autowired
    private UserLikeCommentListService userlikecommentlistService;
    @Autowired
    private SensitiveWordService sensitiveWordService;
    @Autowired
    private SensitiveWordDao sensitiveWordDao;
    @Autowired
    private NewcategoryDao newcategoryDao;
    @Autowired
    private CommentreportService commentreportService ;
    @Autowired
    private CommentreportDao commentreportDao;


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
        List<Newcategory> category=newcategoryDao.findAll();
        model.addAttribute("category",category);
        return "index";
    }
    @RequestMapping(value = {"/newsmanage.html", "/newsmanage","/admincenter/newsmanage.html"})
    public String newsmanage(Model m) {
        PageInfo<News> pageInfo = newsService.findPage(1,10);//将新闻信息送往前端
        List<News> news= pageInfo.getList();
        List<SensitiveWord> words = sensitiveWordDao.findAll();
        List<Newcategory> category=newcategoryDao.findAll();
        m.addAttribute("category",category);
        m.addAttribute("words",words);
        m.addAttribute("pageInfo",pageInfo);
        m.addAttribute("news", news);
        return "newsmanage";
    }
    @RequestMapping(value = {"/newsmanage/list"})//新闻管理分页
    public String newspage(Model m,
                            @RequestParam(defaultValue = "1")int pageNum) {
        PageInfo<News> pageInfo = newsService.findPage(pageNum,10);//将用户信息送往前端
        List<News> news= pageInfo.getList();
        m.addAttribute("pageInfo",pageInfo);
        m.addAttribute("news", news);
        List<Newcategory> category=newcategoryDao.findAll();
        m.addAttribute("category",category);
        return "newsmanage";
    }

    //删除敏感词
    @DeleteMapping("/wordsdelete/{word_id}")
    public String deleteWords(@PathVariable("word_id") Integer word_id,RedirectAttributes model) {
        if (word_id != null) {
            sensitiveWordDao.deleteById(word_id);
            model.addFlashAttribute("result", "删除成功！");
        } else {
            model.addFlashAttribute("result", "敏感词不存在！");
        }
        return "redirect:/newsmanage";
    }
    //添加敏感词
    @PostMapping(value = "/addwords")
    public String AddWords(RedirectAttributes model,
                              String words) {
        if(StringUtils.isBlank(words))
        {
            model.addFlashAttribute("result","填入内容不能为空！");
            return "redirect:/newsmanage";
        }
        else
        {
            String result= sensitiveWordService.addword(words);
            model.addFlashAttribute("result",result);
            return "redirect:/newsmanage";
        }

    }
    //添加板块
    @PostMapping(value = "/addcategory")
    public String AddCategory(RedirectAttributes model,
                           String catename) {
        if(StringUtils.isBlank(catename))
        {
            model.addFlashAttribute("result","填入内容不能为空！");
            return "redirect:/newsmanage";
        }
        else
        {
            newcategoryDao.addNewcate(catename);
            model.addFlashAttribute("result","添加成功！");
            return "redirect:/newsmanage";
        }

    }
    @DeleteMapping("/catedelete/{cate_id}")//删除板块
    public String deleteCate(@PathVariable("cate_id") Integer cate_id, RedirectAttributes model) {
        if (cate_id != null) {
            Newcategory category=newcategoryDao.getOneCateByID(cate_id);
            String catename=category.getName();
            newsDao.deleteByCate(catename);
            newcategoryDao.deleteById(cate_id);
            model.addFlashAttribute("result", "删除成功！");
        } else {
            model.addFlashAttribute("result", "板块不存在！");
        }
        return "redirect:/newsmanage";
    }

    //新闻发布
    @RequestMapping(value = {"/news_publish","/news_publish.html"})
    public String newspub (Model m) {
        List<Newcategory> categorys=newcategoryDao.findAll();
        m.addAttribute("categorys",categorys);
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
            String newcontent=sensitiveWordService.wordsfilter(contents);//过滤内容文本
            String result = newsService.publish(title,newcontent,source,category,time);
            model.addAttribute("result",result);
            return "news_publish";
        }

    }

    @DeleteMapping("/newsdelete/{n_id}")//删除新闻
    public String deleteNews(@PathVariable("n_id") Integer n_id, RedirectAttributes model) {
        if (n_id != null) {
            newsDao.deleteById(n_id);
            model.addFlashAttribute("result", "删除成功！");
        } else {
            model.addFlashAttribute("result", "新闻不存在！");
        }
        return "redirect:/newsmanage";
    }

    @RequestMapping("/newsedit/{n_id}")//编辑新闻页面
    public String newsedits (@PathVariable("n_id") Integer n_id, Model model) {
        News editnews=newsDao.getOneNewsById(n_id);
        model.addAttribute("editnews",editnews);
        model.addAttribute("n_id",n_id);
        List<Newcategory> category=newcategoryDao.findAll();
        model.addAttribute("category",category);
        return "news_edit";
    }

    @PostMapping(value = "/editnews")//编辑新闻
    public String NewsEdit(RedirectAttributes model,int newsid,
                           String title, String contents, String source, String category, String time) {
        if (title == null || title == "" || contents == null || contents == "" || source == "" || source == null || time == null || time == "") {
            model.addFlashAttribute("result", "填入内容不能为空！");
            return "redirect:/newsmanage";
        } else {
            String newcontent=sensitiveWordService.wordsfilter(contents);//过滤内容文本
            News news=newsDao.getOneNewsById(newsid);
            if(news.isIstougao()==true)
            {
                String result = newsService.Edittougao(title, newcontent, source, category, time, newsid);
                model.addFlashAttribute("result", result);
                return "redirect:/newsmanage";
            }
            else
            {
                String result = newsService.Edit(title, newcontent, source, category, time, newsid);
                model.addFlashAttribute("result", result);
                return "redirect:/newsmanage";
            }
        }

    }
    @RequestMapping("/musenews.html")
    public String muse(Model m){
        PageInfo<News> pageInfo = newsService.findPageCate(1,10,"娱乐新闻");//将新闻信息送往前端
        List<News> news= pageInfo.getList();
        List<Newcategory> category=newcategoryDao.findAll();
        m.addAttribute("category",category);
        m.addAttribute("pageInfo",pageInfo);
        m.addAttribute("news", news);
        return "musenews";
    }
    @RequestMapping(value = {"/musenews/list"})
    public String musepage(Model m,
                            @RequestParam(defaultValue = "1")int pageNum) {
        PageInfo<News> pageInfo = newsService.findPageCate(pageNum,10,"娱乐新闻");//将新闻信息送往前端
        List<News> news= pageInfo.getList();
        List<Newcategory> category=newcategoryDao.findAll();
        m.addAttribute("category",category);
        m.addAttribute("pageInfo",pageInfo);
        m.addAttribute("news", news);
        return "musenews";
    }
    @RequestMapping("/financenews.html")
    public String finance(Model m){
        PageInfo<News> pageInfo = newsService.findPageCate(1,10,"财经新闻");//将新闻信息送往前端
        List<News> news= pageInfo.getList();
        List<Newcategory> category=newcategoryDao.findAll();
        m.addAttribute("category",category);
        m.addAttribute("pageInfo",pageInfo);
        m.addAttribute("news", news);
        return "financenews";
    }
    @RequestMapping(value = {"/financenews/list"})
    public String financepage(Model m,
                           @RequestParam(defaultValue = "1")int pageNum) {
        PageInfo<News> pageInfo = newsService.findPageCate(pageNum,10,"财经新闻");//将新闻信息送往前端
        List<News> news= pageInfo.getList();
        List<Newcategory> category=newcategoryDao.findAll();
        m.addAttribute("category",category);
        m.addAttribute("pageInfo",pageInfo);
        m.addAttribute("news", news);
        return "financenews";
    }
    @RequestMapping("/sportsnews.html")
    public String sport(Model m){
        PageInfo<News> pageInfo = newsService.findPageCate(1,10,"体育新闻");//将新闻信息送往前端
        List<News> news= pageInfo.getList();
        m.addAttribute("pageInfo",pageInfo);
        List<Newcategory> category=newcategoryDao.findAll();
        m.addAttribute("category",category);
        m.addAttribute("news", news);
        return "sportsnews";
    }
    @RequestMapping(value = {"/sportsnews/list"})
    public String sportspage(Model m,
                              @RequestParam(defaultValue = "1")int pageNum) {
        PageInfo<News> pageInfo = newsService.findPageCate(pageNum,10,"体育新闻");//将新闻信息送往前端
        List<News> news= pageInfo.getList();
        m.addAttribute("pageInfo",pageInfo);
        List<Newcategory> category=newcategoryDao.findAll();
        m.addAttribute("category",category);
        m.addAttribute("news", news);
        return "sportsnews";
    }

    @RequestMapping("/technews.html")
    public String tech(Model m){
        PageInfo<News> pageInfo = newsService.findPageCate(1,10,"科技新闻");//将新闻信息送往前端
        List<News> news= pageInfo.getList();
        m.addAttribute("pageInfo",pageInfo);
        List<Newcategory> category=newcategoryDao.findAll();
        m.addAttribute("category",category);
        m.addAttribute("news", news);
        return "technews";
    }
    @RequestMapping(value = {"/technews/list"})
    public String techpage(Model m,
                              @RequestParam(defaultValue = "1")int pageNum) {
        PageInfo<News> pageInfo = newsService.findPageCate(pageNum,10,"科技新闻");//将新闻信息送往前端
        List<News> news= pageInfo.getList();
        m.addAttribute("pageInfo",pageInfo);
        List<Newcategory> category=newcategoryDao.findAll();
        m.addAttribute("category",category);
        m.addAttribute("news", news);
        return "technews";
    }
    @RequestMapping("/militarynews.html")
    public String military(Model m){
        PageInfo<News> pageInfo = newsService.findPageCate(1,10,"军事新闻");//将新闻信息送往前端
        List<News> news= pageInfo.getList();
        m.addAttribute("pageInfo",pageInfo);
        List<Newcategory> category=newcategoryDao.findAll();
        m.addAttribute("category",category);
        m.addAttribute("news", news);
        return "militarynews";
    }
    @RequestMapping(value = {"/militarynews/list"})
    public String militarypage(Model m,
                              @RequestParam(defaultValue = "1")int pageNum) {
        PageInfo<News> pageInfo = newsService.findPageCate(pageNum,10,"军事新闻");//将新闻信息送往前端
        List<News> news= pageInfo.getList();
        m.addAttribute("pageInfo",pageInfo);
        List<Newcategory> category=newcategoryDao.findAll();
        m.addAttribute("category",category);
        m.addAttribute("news", news);
        return "militarynews";
    }
    @RequestMapping("/newsextra/{cate_id}")//额外新闻模块
    public String newsextra (@PathVariable("cate_id") Integer cate_id, @RequestParam(defaultValue = "1")int pageNum,Model m,HttpServletRequest request) {
        Newcategory category=newcategoryDao.getOneCateByID(cate_id);
        String catename=category.getName();
        PageInfo<News> pageInfo = newsService.findPageCate(pageNum,10,catename);//将新闻信息送往前端
        List<News> news= pageInfo.getList();
        m.addAttribute("pageInfo",pageInfo);
        m.addAttribute("thiscate",category);
        List<Newcategory> categorys=newcategoryDao.findAll();
        m.addAttribute("category",categorys);
        m.addAttribute("news", news);
        return "extra_news";
    }
    @RequestMapping("/newsitem/{n_id}")
    public String newsshow (@PathVariable("n_id") Integer n_id, @RequestParam(defaultValue = "1")int pageNum,Model model,HttpServletRequest request) {
        PageInfo<Comment> pageInfo = CommentService.findPageNid(pageNum, 10, n_id);
        List<Comment> comment = pageInfo.getList();
        User user = (User) request.getSession().getAttribute("user");
        News news = newsDao.getOneNewsById(n_id);
        if (user != null) {
            for (Comment x : comment) {
                if (userlikecommentlistService.checkIfLike(user.getU_id(), x.getC_id())) {
                    x.setIslike(true);
                }
            }
            if (collectionservice.checkIfcolled(user.getU_id(), n_id)) {
                news.setIscollected(true);
            }
        }
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("comment", comment);
        model.addAttribute("news", news);
        model.addAttribute("n_id", n_id);
        return "page_news_item";
    }
    @PostMapping(value = "/searchnews")//新闻搜索
    public String commentPublish(Model model, String newsname
    ) {
        List<News> news=newsDao.searchnews(newsname);
        model.addAttribute("news",news);
        model.addAttribute("newsname",newsname);
        List<Newcategory> category=newcategoryDao.findAll();
        model.addAttribute("category",category);
        return "extra_search";
    }
    @PostMapping(value = "/collectnews")//收藏新闻
    public String collectionadd(RedirectAttributes model, Integer u_id, Integer n_id, String title
    ) {
        if (u_id == null) {
            model.addFlashAttribute("result", "请登录后再使用收藏！");
            return "redirect:/newsitem/" + n_id;
        } else {
            if (collectionservice.checkIfcolled(u_id, n_id)) {
                //如果已经收藏过了就取消收藏
                Collection collection = collectionDao.checkIfColleted(u_id, n_id);
                collectionDao.deleteById(collection.getC_id());
                //model.addFlashAttribute("已经收藏过啦！");
            } else {
                //还未收藏就加入收藏
                collectionDao.addcollection(u_id, title, n_id);
                //model.addFlashAttribute("result", "收藏成功");
            }        
            return "redirect:/newsitem/" + n_id;
        }
    }

    @RequestMapping(value = {"/adminmail","/adminmail.html"})//管理员信箱
    public String adminmailer(Model model)
    {
        List<Commentreport> report=commentreportDao.findAll();
        PageInfo<News> pageInfo = newsService.findtougaoPage(1,10);//将新闻信息送往前端
        List<News> news= pageInfo.getList();
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("news", news);
        model.addAttribute("report",report);
        List<Newcategory> category=newcategoryDao.findAll();
        model.addAttribute("category",category);
        return "adminmail";
    }
    @RequestMapping(value = {"/newsmail/list"})//投稿分页
    public String mailpage(Model m,
                           @RequestParam(defaultValue = "1")int pageNum) {
        PageInfo<News> pageInfo = newsService.findtougaoPage(pageNum,10);//将用户信息送往前端
        List<News> news= pageInfo.getList();
        m.addAttribute("pageInfo",pageInfo);
        m.addAttribute("news", news);
        List<Newcategory> category=newcategoryDao.findAll();
        m.addAttribute("category",category);
        return "adminmail";
    }

    //投稿功能
    @RequestMapping(value = {"/contributesubmit","/contributesubmit.html"})
    public String contribute (Model m) {
        List<Newcategory> category=newcategoryDao.findAll();
        m.addAttribute("category",category);
        return "contributesubmit";
    }
    @PostMapping(value = "/submitcontribute")
    public String contributesubmit(RedirectAttributes model,
                              String title, String contents, String source, String category, String time) {
        if(title == null || title=="" || contents == null ||contents =="" ||source ==""||source==null||time==null||time=="")
        {
            model.addFlashAttribute("result","填入内容不能为空！");
            return "index";
        }
        else
        {
            String result = newsService.contributesubmit(title,contents,source,category,time);
            model.addFlashAttribute("result",result);
            return "index";
        }

    }

    
}
