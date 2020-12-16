package com.example.demo.dao;

import com.example.demo.model.News;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface NewsDao {
    String TABLE_NAME = "news";
    String INSERT_FIELDS = " title, time, source ,category ,contents";
    String INSERT_FIELDS2 = " title, time, source ,category ,contents, istougao";

    @Select("select * from news where title=#{title} limit 1")
    News getOneNews(String title);
    @Select("select * from news where n_id=#{id} limit 1")
    News getOneNewsById(Integer id);
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{title},#{time},#{source},#{category},#{contents})"})
    int addNews(String title, String contents, String source, String category, String time);

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS2,
            ") values (#{title},#{time},#{source},#{category},#{contents},1)"})
    int addcontribute(String title, String contents, String source, String category, String time);
    @Select("select * from news where istougao=0")
    List<News> findAll();

    @Select("select * from news where istougao=1")
    List<News> findtougaoAll();

    @Select("SELECT * FROM news where title like '%${newsname}%' and istougao=0")
    List<News> searchnews(String newsname);

    @Select("select * from news where category=#{category} and istougao=0 order by n_id desc ")
    List<News> findnewsbycate(String category);

    @Select("select * from news where category=#{category}")
    List<News> findnewsbycategory(String category);

    @Select("select * from news where category=#{category} and istougao=0 order by n_id desc limit 0,#{num}")
    List<News> findNews(String category,int num);

    @Update({"update ", TABLE_NAME, " set title=#{title} ,contents=#{contents} ,source=#{source} ,category=#{category} ,time=#{time} where n_id=#{id}"})
    void updateNews(String title, String contents, String source, String category, String time,Integer id);

    @Update({"update ", TABLE_NAME, " set title=#{title} ,contents=#{contents} ,source=#{source} ,category=#{category} ,time=#{time},istougao=0 where n_id=#{id}"})
    void updateTougao(String title, String contents, String source, String category, String time,Integer id);

    @Delete({"delete from ", TABLE_NAME, " where n_id=#{id}"})
    void deleteById(int id);

    @Delete({"delete from news where category=#{category}"})
    void deleteByCate(String category);



}
