package com.example.demo.dao;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import com.example.demo.model.SensitiveWord;
import java.util.List;

@Mapper
@Component
public interface SensitiveWordDao {
    String TABLE_NAME = "sensitiveword";
    String INSERT_FIELDS = " word";

    @Select("select word from sensitiveword")
    List<String> findAllWords();

    @Select("select * from sensitiveword")
    List<SensitiveWord> findAll();
    @Delete({"delete from ", TABLE_NAME, " where word_id=#{id}"})
    void deleteById(int id);

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{word})"})
    int addword(String word);
}
