package com.example.demo.services;

import com.example.demo.dao.CommentreportDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentreportService {
    @Autowired
    private CommentreportDao commentreportDao;

    // 用户举报评论逻辑
    public void report(String u1_name, String u_name,int n_id) {
        commentreportDao.addReport(u1_name, u_name, n_id);
    }

}
