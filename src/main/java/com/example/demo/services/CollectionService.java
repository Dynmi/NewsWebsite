package com.example.demo.services;

import com.example.demo.dao.CollectionDao;
import com.example.demo.model.Collection;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionService {
    @Autowired
    private CollectionDao collectionDao;
    //分页收藏逻辑
    public PageInfo findCollectionuid(int page, int pagesize, int u_id) {
        PageHelper.startPage(page, pagesize);
        List<Collection> list = collectionDao.findcollection(u_id);
        PageInfo<Collection> pageInfo = new PageInfo<>(list);//将list（全部结果传进去）范型根据情况而定
        return pageInfo;//返回pageInfo对象给controller
    }

    //查看当前用户是否已经收藏了该新闻
    public boolean checkIfcolled(int u_id, int n_id) {
        if (collectionDao.checkIfColleted(u_id, n_id) != null) {
            return true;//没有收藏过返回fasle
        } else
            return false;//收藏过了返回true
    }
    

}
