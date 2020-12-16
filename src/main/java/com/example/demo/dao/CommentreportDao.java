package com.example.demo.dao;

import com.example.demo.model.Commentreport;
import com.example.demo.model.News;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CommentreportDao {
    String TABLE_NAME = "commentreport";
    String INSERT_FIELDS = "u1_name,u_name,n_id";

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,") values(#{u1_name},#{u_name},#{n_id})"})
    int addReport(String u1_name, String u_name,int n_id);

    @Select({"select * from ",TABLE_NAME,"where u1_name=#{u1_name} and u_name=#{u_name}"})
    Commentreport getOneReport(String u1_name, String u_name);

    @Delete({"delete from ",TABLE_NAME,"where report_id=#{id}"})
    void deleteById(int id);

    @Select("select * from commentreport")
    List<Commentreport> findAll();
}
