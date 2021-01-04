package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileWrapper {

    @Insert("INSERT INTO FILES (fileName, contentType, fileSize,userId,fileData) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    boolean insert(File File);

    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<File> getFiles(Integer userId);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFile(Integer fileId);

    @Select("SELECT * FROM FILES WHERE fileName = #{fileName}")
    File getFilebyName(String fileName);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    boolean delete(Integer fileId);
}
