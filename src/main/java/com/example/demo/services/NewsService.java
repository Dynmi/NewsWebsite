package com.example.demo.services;

import com.example.demo.dao.NewsDao;
import com.example.demo.model.News;
import com.example.demo.model.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class NewsService {
    //自动注入一个userDao
    @Autowired
    private NewsDao newsDao;

    //新闻发布逻辑
    public String publish(String title, String contents, String source, String category, String time) {
        //判断标题是否存在
        if (newsDao.getOneNews(title) == null) {
            newsDao.addNews(title,contents,source,category,time);
            return "发布成功！";
        }
        else {
            return "已存在相同标题新闻！";
        }
    }

    //新闻发布逻辑
    public String Edit(String title, String contents, String source, String category, String time,int id) {
        //判断标题是否存在
        if (newsDao.getOneNews(title) == null) {
            newsDao.updateNews(title,contents,source,category,time,id);
            return "修改成功！";
        }
        else {
            return "已存在相同标题新闻！";
        }
    }

    //分页逻辑
    public PageInfo findPage(int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        List<News> list=newsDao.findAll();
        PageInfo<News> pageInfo=new PageInfo<>(list); //将list（全部结果传进去） 泛型根据情况而定
        return pageInfo;  //返回pageInfo对象给controller
    }
    //分页逻辑2
    public PageInfo findPageCate(int page, int pageSize,String category) {
        PageHelper.startPage(page,pageSize);
        List<News> list=newsDao.findnewsbycate(category);
        PageInfo<News> pageInfo=new PageInfo<>(list); //将list（全部结果传进去） 泛型根据情况而定
        return pageInfo;  //返回pageInfo对象给controller
    }

    //喜好逻辑
   public List<News> favornews(String favor)
   {
       List<News> list1;
       List<News> list2;
       List<News> list3;
       List<News> list4;
       List<News> list5;
       switch (favor){
           case "1,2,3,4,5":
           default:{
               list1=newsDao.findNews("娱乐新闻",2);
               list2=newsDao.findNews("体育新闻",2);
               list3=newsDao.findNews("科技新闻",2);
               list4=newsDao.findNews("军事新闻",2);
               list5=newsDao.findNews("财经新闻",2);
               list1.addAll(list2);
               list1.addAll(list3);
               list1.addAll(list4);
               list1.addAll(list5);
               return list1;
           }
           case "2,3,4,5":
           {
               list2=newsDao.findNews("体育新闻",3);
               list3=newsDao.findNews("科技新闻",2);
               list4=newsDao.findNews("军事新闻",2);
               list5=newsDao.findNews("财经新闻",3);
               list2.addAll(list3);
               list2.addAll(list4);
               list2.addAll(list5);
               return list2;
           }
           case "1,3,4,5":
           {
               list1=newsDao.findNews("娱乐新闻",3);
               list3=newsDao.findNews("科技新闻",3);
               list4=newsDao.findNews("军事新闻",2);
               list5=newsDao.findNews("财经新闻",2);
               list1.addAll(list3);
               list1.addAll(list4);
               list1.addAll(list5);
               return list1;
           }
           case "1,2,4,5":
           {
               list1=newsDao.findNews("娱乐新闻",3);
               list2=newsDao.findNews("体育新闻",3);
               list4=newsDao.findNews("军事新闻",2);
               list5=newsDao.findNews("财经新闻",2);
               list1.addAll(list2);
               list1.addAll(list4);
               list1.addAll(list5);
               return list1;
           }
           case "1,2,3,5":
           {
               list1=newsDao.findNews("娱乐新闻",3);
               list2=newsDao.findNews("体育新闻",2);
               list3=newsDao.findNews("科技新闻",3);
               list5=newsDao.findNews("财经新闻",2);
               list1.addAll(list2);
               list1.addAll(list3);
               list1.addAll(list5);
               return list1;
           }
           case "1,2,3,4":
           {
               list1=newsDao.findNews("娱乐新闻",3);
               list2=newsDao.findNews("体育新闻",2);
               list3=newsDao.findNews("科技新闻",2);
               list4=newsDao.findNews("军事新闻",3);
               list1.addAll(list2);
               list1.addAll(list3);
               list1.addAll(list4);
               return list1;
           }
           case "3,4,5": {
               list3=newsDao.findNews("科技新闻",3);
               list4=newsDao.findNews("军事新闻",3);
               list5=newsDao.findNews("财经新闻",4);
               list3.addAll(list4);
               list3.addAll(list5);
               return list3;
           }
           case "2,4,5": {
               list2=newsDao.findNews("体育新闻",3);
               list4=newsDao.findNews("军事新闻",4);
               list5=newsDao.findNews("财经新闻",3);
               list2.addAll(list4);
               list2.addAll(list5);
               return list2;
           }
           case "2,3,5": {
               list2=newsDao.findNews("体育新闻",3);
               list3=newsDao.findNews("科技新闻",4);
               list5=newsDao.findNews("财经新闻",3);
               list2.addAll(list3);
               list2.addAll(list5);
               return list2;
           }
           case "2,3,4": {
               list2=newsDao.findNews("体育新闻",4);
               list3=newsDao.findNews("科技新闻",3);
               list4=newsDao.findNews("军事新闻",3);
               list2.addAll(list3);
               list2.addAll(list4);
               return list2;
           }
           case "1,4,5":{
               list1=newsDao.findNews("娱乐新闻",4);
               list4=newsDao.findNews("军事新闻",3);
               list5=newsDao.findNews("财经新闻",3);
               list1.addAll(list4);
               list1.addAll(list5);
               return list1;
           }
           case "1,3,5":{
               list1=newsDao.findNews("娱乐新闻",3);
               list3=newsDao.findNews("科技新闻",4);
               list5=newsDao.findNews("财经新闻",3);
               list1.addAll(list3);
               list1.addAll(list5);
               return list1;
           }
           case "1,3,4":{
               list1=newsDao.findNews("娱乐新闻",4);
               list3=newsDao.findNews("科技新闻",3);
               list4=newsDao.findNews("军事新闻",3);
               list1.addAll(list3);
               list1.addAll(list4);
               return list1;
           }
           case "1,2,5":{
               list1=newsDao.findNews("娱乐新闻",3);
               list2=newsDao.findNews("体育新闻",4);
               list5=newsDao.findNews("财经新闻",3);
               list1.addAll(list2);
               list1.addAll(list5);
               return list1;
           }
           case "1,2,4":{
               list1=newsDao.findNews("娱乐新闻",3);
               list2=newsDao.findNews("体育新闻",4);
               list4=newsDao.findNews("军事新闻",3);
               list1.addAll(list2);
               list1.addAll(list4);
               return list1;
           }
           case "1,2,3":{
               list1=newsDao.findNews("娱乐新闻",4);
               list2=newsDao.findNews("体育新闻",3);
               list3=newsDao.findNews("科技新闻",3);
               list1.addAll(list2);
               list1.addAll(list3);
               return list1;
           }
           case "1,2":{
               list1=newsDao.findNews("娱乐新闻",5);
               list2=newsDao.findNews("体育新闻",5);
               list1.addAll(list2);
               return list1;
           }
           case "1,3":{
               list1=newsDao.findNews("娱乐新闻",5);
               list3=newsDao.findNews("科技新闻",5);
               list1.addAll(list3);
               return list1;
           }
           case "1,4":{
               list1=newsDao.findNews("娱乐新闻",5);
               list4=newsDao.findNews("军事新闻",5);
               list1.addAll(list4);
               return list1;
           }
           case "1,5":{
               list1=newsDao.findNews("娱乐新闻",5);
               list5=newsDao.findNews("财经新闻",5);
               list1.addAll(list5);
               return list1;
           }
           case "2,3":{
               list2=newsDao.findNews("体育新闻",5);
               list3=newsDao.findNews("科技新闻",5);
               list2.addAll(list3);
               return list2;
           }
           case "2,4":{
               list2=newsDao.findNews("体育新闻",5);
               list4=newsDao.findNews("军事新闻",5);
               list2.addAll(list4);
               return list2;
           }
           case "2,5":{
               list2=newsDao.findNews("体育新闻",5);
               list5=newsDao.findNews("财经新闻",5);
               list2.addAll(list5);
               return list2;
           }
           case "3,4":{
               list3=newsDao.findNews("科技新闻",5);
               list4=newsDao.findNews("军事新闻",5);
               list3.addAll(list4);
               return list3;
           }
           case "3,5":{
               list3=newsDao.findNews("科技新闻",5);
               list5=newsDao.findNews("财经新闻",5);
               list3.addAll(list5);
               return list3;
           }
           case "4,5":{
               list4=newsDao.findNews("军事新闻",5);
               list5=newsDao.findNews("财经新闻",5);
               list4.addAll(list5);
               return list4;
           }
           case "1":{
               list1=newsDao.findNews("娱乐新闻",10);
               return list1;
           }
           case "2":{
               list2=newsDao.findNews("体育新闻",10);
               return list2;
           }
           case "3":{
               list3=newsDao.findNews("科技新闻",10);
               return list3;
           }
           case "4":{
               list4=newsDao.findNews("军事新闻",10);
               return list4;
           }
           case "5":{
               list5=newsDao.findNews("财经新闻",10);
               return list5;
           }

        }
    }

}
