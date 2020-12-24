package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

/**
 * a MyBatis Mapper for SQL table 'USERS'
 * unfinished: need an update operation?
 */
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUserByUsername (String username);

    @Select("SELECT * FROM USERS WHERE userid = #{userid}")
    User getUserByUserid (Integer userid);

    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES (#{username}, #{salt}, #{password}, #{firstname}, #{lastname})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    Integer addUser (User user);

    @Delete("DELETE FROM USERS WHERE userid = #{userid}")
    void deleteUser (Integer userid);
}
