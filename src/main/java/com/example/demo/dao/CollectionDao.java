package com.example.demo.dao;

import com.example.demo.model.Collection;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CollectionDao {
    String TABLE_NAME = "collection";
    String INSERT_FIELDS = "u_id,title,n_id";
    
    @Insert({ "insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{u_id},#{title},#{n_id})" })
    void addcollection(int u_id, String title, int n_id);
    
    @Select("select * from collection where u_id=#{userid} order by c_id desc ")
    List<Collection> findcollection(Integer userid);
    
    @Select({ "select *from ", TABLE_NAME, "where u_id=#{u_id} and n_id=#{n_id}" })
    Collection checkIfColleted(int u_id, int n_id);

    @Delete({"delete from ", TABLE_NAME, " where c_id=#{id}"})
    void deleteById(int id);

}
