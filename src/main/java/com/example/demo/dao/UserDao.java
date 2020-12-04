package com.example.demo.dao;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserDao {
    String TABLE_NAME = "user";
    String INSERT_FIELDS = " role, username, password, favourite, collection ,permission";

    @Select("select * from user where username=#{username} limit 1")
    User getOneUser(String username);
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{role},#{username},#{password},#{favourite},#{collection},#{permission})"})
    int addUser(User user);


    @Update({"update ", TABLE_NAME, " set password=#{password} where id=#{id}"})
    void updatePassword(User user);

    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);


}
