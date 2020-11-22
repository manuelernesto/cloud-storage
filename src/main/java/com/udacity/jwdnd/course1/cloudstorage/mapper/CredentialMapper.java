package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credential> getCredentials(Integer userid);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    Credential getCredential(Integer credentialid);

    @Insert("INSERT INTO CREDENTIALS (url, username, key,password,userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    void insert(Credential credential);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username},key = #{key}, password = #{password}, userid =  #{userid} WHERE credentialid = #{credentialid}")
    void update(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    void delete(Integer credentialid);

}
