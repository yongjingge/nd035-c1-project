package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * a MyBatis Mapper for SQL table 'FILES'
 */
@Mapper
public interface FileMapper {

    // get files for a specific user
    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<File> getFilesByUserid (Integer userid);

    // get a specific file by its id
    @Select("SELECT * FROM FILES WHERE fileid = #{fileid}")
    File getFileByFileid (Integer fileid);

    // get a specific file by its name and userid
    @Select("SELECT * FROM FILES WHERE filename = #{filename} and userid = #{userid}")
    File getFileByFilenameAndUserid (String filename, Integer userid);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileid")
    Integer addFile (File file);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileid}")
    void deleteByFileid (Integer fileid);
}
