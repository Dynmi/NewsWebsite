package com.example.demo.dao;

import com.example.demo.model.Comment;
import com.example.demo.model.News;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CommentDao {
    String TABLE_NAME = "comment";
    String INSERT_FIELDS = "u_id,contents,n_id";

    @Select("select *from comment where n_id=#{n_id}")
    @Results({
            @Result(column = "c_id",property = "c_id"),
            @Result(column = "likes",property="likes"),
            @Result(column = "n_id",property = "n_id"),
            @Result(column = "time",property = "time"),
            @Result(column = "contents",property = "contents"),
            @Result(column = "u_id", property = "author", one = @One(select = "com.example.demo.dao.UserDao.getUserById"))
         })
    List<Comment> findCommentByNid(int n_id);

    @Insert({ "insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{u_id},#{contents},#{n_id})" })
    void insertComment(int u_id, String contents, int n_id);

}
