package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileWrapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private final FileWrapper fileWrapper;

    public FileService(FileWrapper fileWrapper) {
        this.fileWrapper = fileWrapper;
    }

    public boolean save(File file) throws SizeLimitExceededException {
        if (isFileExist(file))
            return false;

        return fileWrapper.insert(file);
    }

    public boolean delete(Integer fileId) {
        return fileWrapper.delete(fileId);
    }

    public List<File> getAllFiles(Integer userId) {
        return fileWrapper.getFiles(userId);
    }

    public File getFile(Integer fileId) {
        return fileWrapper.getFile(fileId);
    }

    private boolean isFileExist(File file) {
        File fileExist = fileWrapper.getFilebyName(file.getFileName());
        return fileExist != null;
    }
}
