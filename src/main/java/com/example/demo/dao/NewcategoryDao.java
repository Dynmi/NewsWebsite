package com.example.demo.dao;
import com.example.demo.model.Newcategory;
import com.example.demo.model.News;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component
public interface NewcategoryDao {
    String TABLE_NAME = "newcategory";
    String INSERT_FIELDS = " name";

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{name})"})
    int addNewcate(String name);

    @Delete({"delete from ", TABLE_NAME, " where cate_id=#{id}"})
    void deleteById(int id);

    @Select("select * from newcategory")
    List<Newcategory> findAll();

    @Select("select name from newcategory")
    List<String> findAllNewcate();

    @Select("select * from newcategory where name=#{name} limit 1")
    Newcategory getOneCate(String name);

    @Select("select * from newcategory where cate_id=#{id} limit 1")
    Newcategory getOneCateByID(int id);
}
