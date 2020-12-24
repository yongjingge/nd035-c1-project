package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * a MyBatis Mapper for SQL table 'NOTES'
 */
@Mapper
public interface NoteMapper {

    // get notes for a specific user
    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Note> getNotesByUserid (Integer userid);

    // get a specific note by its id
    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    Note getNoteByNoteid (Integer noteid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    Integer addNote (Note note);

    // modifying a note by its title, description.
    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription} WHERE noteid = #{noteid} and userid = #{userid}")
    void updateNote (Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    void deleteByNoteid (Integer noteid);
}
