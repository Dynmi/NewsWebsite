package com.example.demo.services;

import com.example.demo.dao.CommentDao;
import com.example.demo.dao.UserLikeCommentListDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLikeCommentListService {
    @Autowired
    private UserLikeCommentListDao userLikeCommentListDao;

    @Autowired
    private CommentDao commentDao;
    //用户点赞评论逻辑
    public void like(int u_id, int c_id) {
        commentDao.changeCommentLikes(1, c_id);
        userLikeCommentListDao.insertLike(u_id, c_id);
    }

    //判断用户是否点赞了该评论
    public boolean checkIfLike(int u_id, int c_id) {
        //UserLikeCommentList userLikeCommentList = userLikeCommentListDao.checkUserLikeComment(u_id, c_id);
        if (userLikeCommentListDao.checkUserLikeComment(u_id, c_id) != null)
            return true;
        else
            return false;
    }

    //取消用户点赞逻辑
    public void notlike(int u_id, int c_id) {
        commentDao.changeCommentLikes(-1, c_id);
        userLikeCommentListDao.deletelike(u_id, c_id);
    }

}
