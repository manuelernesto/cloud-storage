package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Controller()
@RequestMapping()
public class FileController {
    private final UserService userService;
    private final FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }


    @PostMapping("/file-save")
    public String addFile(Authentication auth,
                          @RequestParam("fileUpload") MultipartFile fileUpload,
                          Model model) {

        Boolean isSuccess = null;

        var file = new File();
        var user = userService.getUser(auth.getName());

        if (Objects.requireNonNull(fileUpload.getOriginalFilename()).isEmpty()) {
            isSuccess = false;
        } else {
            try {
                file.setUserId(user.getUserId());
                file.setFileData(fileUpload.getBytes());
                file.setFileName(fileUpload.getOriginalFilename());
                file.setContentType(fileUpload.getContentType());
                file.setFileSize(fileUpload.getSize() + "");

                isSuccess = fileService.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return "redirect:/result?isSuccess=" + isSuccess;
    }


    @GetMapping("/file-view")
    public void viewFile(@RequestParam("fileId") Integer fileId, HttpServletResponse response) {
        var file = fileService.getFile(fileId);

        if (file != null) {
            try {
                response.setContentType(file.getContentType());
                response.setContentLength(Integer.parseInt(file.getFileSize()));
                response.setHeader("Content-Disposition", "attachment; filename=" + file.getFileName());

                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(file.getFileData());

                response.flushBuffer();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @PostMapping("/file-delete")
    public String deleteFile(@RequestParam Integer fileId) {
        var isSuccess = fileService.delete(fileId);
        return "redirect:/result?isSuccess=" + isSuccess;
    }


}
