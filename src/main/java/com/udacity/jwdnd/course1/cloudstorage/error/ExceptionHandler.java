package com.udacity.jwdnd.course1.cloudstorage.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ExceptionHandler {


    @org.springframework.web.bind.annotation.ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleError(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorUpload", "You could not upload file bigger than 10MB.");
        return "redirect:/error";

    }
}
