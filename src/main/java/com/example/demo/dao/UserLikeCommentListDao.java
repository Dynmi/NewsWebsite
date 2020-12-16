package com.example.demo.dao;

import com.example.demo.model.UserLikeCommentList;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserLikeCommentListDao {
    String TABLE_NAME="UserLikeCommentList";
    String INSERT_FIELDS="u_id,c_id";

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,") values (#{u_id},#{c_id})"})
    void insertLike(int u_id,int c_id);

    @Select({ "select *from ", TABLE_NAME, "where u_id=#{u_id} and c_id=#{c_id}" })
    UserLikeCommentList checkUserLikeComment(int u_id, int c_id);
    
    @Delete({"delete from ",TABLE_NAME," where u_id=#{u_id} and c_id=#{c_id} "})
    void deletelike(int u_id,int c_id);
}
