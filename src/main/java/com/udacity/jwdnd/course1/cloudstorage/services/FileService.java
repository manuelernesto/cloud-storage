package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileWrapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
 private final FileWrapper fileWrapper;

    public FileService(FileWrapper fileWrapper) {
        this.fileWrapper = fileWrapper;
    }

    public void save(File file){
        fileWrapper.insert(file);
    }

    public void delete(Integer fileId){
        fileWrapper.delete(fileId);
    }

    public List<File> getAllFiles(Integer userId){
        return fileWrapper.getFiles(userId);
    }

    public File getFile(Integer fileId){
        return fileWrapper.getFile(fileId);
    }
}
