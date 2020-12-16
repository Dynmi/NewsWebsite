package com.example.demo.services;


import com.example.demo.model.News;
import com.example.demo.model.SensitiveWord;
import com.example.demo.dao.SensitiveWordDao;
import com.example.demo.util.NewsFilterUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensitiveWordService {
    //自动注入
    @Autowired
    private SensitiveWordDao sensitiveWordDao;

    //文本过滤
    public String wordsfilter(String text)
    {
        List<String> originwords= sensitiveWordDao.findAllWords();
        String result = NewsFilterUtil.sensitiveHelper(text,originwords);
        return result;
    }

    //添加敏感词
    public String addword(String words)
    {

        sensitiveWordDao.addword(words);
        return "添加成功！";
    }


}
