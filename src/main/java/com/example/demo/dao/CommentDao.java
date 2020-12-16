package com.example.demo.dao;

import com.example.demo.model.Comment;
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

    @Select("select *from comment where c_id=#{c_id} limit 1")
    Comment Getonecomment(int c_id);
    
    @Insert({ "insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{u_id},#{contents},#{n_id})" })
    void insertComment(int u_id, String contents, int n_id);

    //改变评论的点赞数,+1还好是-1
    @Update({ "update ", TABLE_NAME, "set likes=likes+#{change} where c_id=#{c_id}" })
    void changeCommentLikes(int change, int c_id);

    @Delete({"delete from ", TABLE_NAME, " where c_id=#{id}"})
    void deleteCommentById(int id);
}
