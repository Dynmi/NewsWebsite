package com.example.demo.controller;

import com.example.demo.services.UserLikeCommentListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserLikeCommentListController {
    @Autowired
    private UserLikeCommentListService userLikeCommentListService;

    @RequestMapping(value = "/like/{c_id}")
    public String commentLike(@PathVariable("c_id") Integer c_id, Integer u_idnow, Integer n_idnow,
            RedirectAttributes model) {
        if (u_idnow != null) {
            if (userLikeCommentListService.checkIfLike(u_idnow, c_id)) {
                // 如果已经点赞过,就取消点赞
                userLikeCommentListService.notlike(u_idnow, c_id);
                // model.addFlashAttribute("result", "取消点赞成功");
            } else {
                userLikeCommentListService.like(u_idnow, c_id);
                // model.addFlashAttribute("result", "点赞成功！");
            }
        } else {
            model.addFlashAttribute("result", "请登录后再进行操作");
        }

        return "redirect:/newsitem/" + n_idnow;

    }

    
}
