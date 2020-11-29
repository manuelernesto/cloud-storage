package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
                          RedirectAttributes redirectAttributes) {

        var file = new File();
        var user = userService.getUser(auth.getName());

        try {
            file.setUserId(user.getUserId());
            file.setFileData(fileUpload.getBytes());
            file.setFileName(fileUpload.getOriginalFilename());
            file.setContentType(fileUpload.getContentType());
            file.setFileSize(fileUpload.getSize() + "");

            fileService.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/home";
    }


    @GetMapping("/file-view")
    public void viewFile(@RequestParam("fileId") Integer fileId, HttpServletResponse response) {

    }

    @PostMapping("/file-delete")
    public String deleteFile(@RequestParam Integer fileId) {
        fileService.delete(fileId);
        return "redirect:/home";
    }


}
