package com.example.demo.controller;

import com.example.demo.dao.CommentDao;
import com.example.demo.dao.CommentreportDao;
import com.example.demo.dao.UserDao;
import com.example.demo.model.Comment;
import com.example.demo.model.User;
import com.example.demo.services.CommentreportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CommentreportController {
    @Autowired
    private CommentreportService commentreportService;
    @Autowired
    private CommentreportDao commentreportDao;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/commentreport/{c_id}")
    public String commentReport(@PathVariable("c_id") int c_id, String u_name ,Integer n_idnow,
            RedirectAttributes model) {
        Comment a=commentDao.Getonecomment(c_id);
        int uid=a.getU_id();
        if(userDao.getUserById(uid)!=null)
        {
            User users=userDao.getUserById(uid);
            String u1_name=users.getUsername();
            if (u_name != null && u_name !="") {
                commentreportService.report(u_name, u1_name,n_idnow);
                model.addFlashAttribute("result", "举报成功,请等待管理员受理！");
            } else {
                model.addFlashAttribute("result", "请登录后再进行举报操作");
            }
        }
        else
        {
            model.addFlashAttribute("result","该用户已经不存在！");
        }
        return "redirect:/newsitem/" + n_idnow;
    }
    //移除举报
    @DeleteMapping("/reportdelete/{report_id}")
    public String deletecollect(@PathVariable("report_id") Integer report_id, RedirectAttributes model) {
        if (report_id != null) {
            commentreportDao.deleteById(report_id);
        } else {
            model.addFlashAttribute("result", "举报已不存在！");
        }
        return "redirect:/adminmail.html";
    }

}
