package com.example.demo.services;
import com.example.demo.dao.*;
import org.springframework.beans.factory.annotation.Autowired;

public class NewcategoryService {
    @Autowired
    NewcategoryDao newcategoryDao;
    public String addcategory(String name)
    {
        if(newcategoryDao.getOneCate(name)==null)
        {
            newcategoryDao.addNewcate(name);
            return "添加成功！";
        }
        else
        {
            return "已存在相同板块!";
        }
    }
}
