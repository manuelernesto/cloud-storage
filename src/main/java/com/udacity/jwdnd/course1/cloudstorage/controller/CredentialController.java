package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller()
@RequestMapping()
public class CredentialController {

    private final UserService userService;
    private final CredentialService credentialService;

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }


    @PostMapping("/credential-save")
    public String addCredential(Authentication auth,
                                @ModelAttribute Credential credential,
                                RedirectAttributes redirectAttributes) {

        var user = userService.getUser(auth.getName());
        credential.setUserid(user.getUserId());

        var isSuccess = credentialService.saveCredential(credential);

        return "redirect:/result?isSuccess=" + isSuccess;
    }

    @PostMapping("/credential-delete")
    public String deleteNote(@RequestParam Integer credentialid) {
        var isSuccess = credentialService.deleteCredential(credentialid);
        return "redirect:/result?isSuccess=" + isSuccess;
    }
}
