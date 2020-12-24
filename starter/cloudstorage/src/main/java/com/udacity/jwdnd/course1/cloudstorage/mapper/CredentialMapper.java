package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * a MyBatis Mapper for SQL table 'CREDENTIALS'
 */
@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credential> getCredentialsByUserid (Integer userid);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    Credential getCredentialById (Integer credentialid);

    @Insert("INSERT INTO CREDENTIALS (url, username, password, userid, key) VALUES (#{url}, #{username}, #{password}, #{userid}, #{key})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    Integer addCredential (Credential credential);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, password = #{password}, key = #{key} WHERE userid = #{userid}")
    void updateCredential (Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    void deleteCredentialByCredentialid (Integer credentialid);
}
