package com.example.demo.services;

import com.example.demo.dao.CommentDao;
import com.example.demo.model.Comment;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    //自动注入一个commentDao
    @Autowired
    private CommentDao commentDao;

    //评论发布逻辑
    public String publish(int u_id, String contents, int n_id) {
        //
        commentDao.insertComment(u_id, contents, n_id);
        return "评论成功！";
    }
    
    //分页评论逻辑
    public PageInfo findPageNid(int page, int pagesize, int n_id) {
        PageHelper.startPage(page, pagesize);
        List<Comment> list = commentDao.findCommentByNid(n_id);
        PageInfo<Comment> pageInfo = new PageInfo<>(list);//将list（全部结果传进去）范型根据情况而定
        return pageInfo;//返回pageInfo对象给controller
    }
}
