package com.example.demo.controller;

import com.example.demo.dao.CommentDao;
import com.example.demo.dao.NewsDao;
import com.example.demo.dao.UserDao;
import com.example.demo.model.Comment;
import com.example.demo.model.News;
import com.example.demo.model.User;
import com.example.demo.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CommentController {
    // 自动注入各种service模块
    @Autowired
    private CommentService commentService;
    @Autowired
    private NewsDao newsDao;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private UserDao userDao;

    @PostMapping(value = "/publishcomment")
    public String commentPublish(RedirectAttributes modelMap, Integer newsid, String commentuser,
            String contents)  {
        News news = newsDao.getOneNewsById(newsid);
        User user =userDao.getOneUser(commentuser);
        modelMap.addFlashAttribute("news", news); // rediectAttributes能够在页面充电向后保存model的值。而用model的话值会丢失。
        modelMap.addFlashAttribute("n_id", newsid); 
        if (commentuser == null || commentuser == "") {
            modelMap.addFlashAttribute("result", "请登录后再发表回复！");
            return "redirect:/newsitem/" + newsid;
        } else {
            if (contents == null || contents == "") {
                modelMap.addFlashAttribute("result", "评论内容不能为空！");     
                return "redirect:/newsitem/" + newsid;
            } else {
                commentService.publish(user.getU_id(), contents,  newsid);
                modelMap.addFlashAttribute("result", "评论成功！");
                return "redirect:/newsitem/" + newsid;
            }
        }
    }
    @DeleteMapping("/commentdelete/{c_id}")
    public String deletecomment(@PathVariable("c_id") Integer c_id,Integer u_idnow, RedirectAttributes model) {
        Comment comment=commentDao.Getonecomment(c_id);
        Integer u_id=comment.getU_id();
        Integer n_id=comment.getN_id();
        if(u_idnow !=null)
        {
            if (c_id != null ) {
                {
                    User user=userDao.getUserById(u_idnow);
                    if(user.getPermission()==1 || u_id==u_idnow)
                    {
                        commentDao.deleteCommentById(c_id);
                        model.addFlashAttribute("result", "删除成功！");
                    }
                    else
                    {
                        model.addFlashAttribute("result", "你不可以删除他人的评论！");
                    }
                }

            } else {
                model.addFlashAttribute("result", "评论已不存在！");
            }
        }
        else
        {
            model.addFlashAttribute("result", "请登录后再进行操作");
        }
        return "redirect:/newsitem/" + n_id;
    }
}