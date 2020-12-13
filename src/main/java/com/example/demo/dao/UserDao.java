package com.example.demo.dao;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserDao {
    String TABLE_NAME = "user";
    String INSERT_FIELDS = " username, password";

    @Select("select * from user where username=#{username} limit 1")
    User getOneUser(String username);
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{username},#{password})"})
    int addUser(User user);

    @Select("select * from user")
    List<User> findAll();

    @Select("select favourite from user where username=#{username} limit 1")
    String getOneUserFav(String username);

    @Update({"update ", TABLE_NAME, " set password=#{password} where username=#{username}"})
    void updatePassword(String password,String username);

    @Update({"update ", TABLE_NAME, " set username=#{username} where username=#{oldusername}"})
    void updateUsername(String oldusername,String username);

    @Delete({"delete from ", TABLE_NAME, " where username=#{username}"})
    void deleteByName(String username);

    @Update({"Update ", TABLE_NAME, " set favourite=#{fav} where username=#{username}"})
    void setFav(String fav,String username);


}
