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
        long maxFileSize = 10485760;
        long fileSize = Long.parseLong(file.getFileSize());

        if (maxFileSize < fileSize)
            throw new SizeLimitExceededException("", fileSize, maxFileSize);

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
}
