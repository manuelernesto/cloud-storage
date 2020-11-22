package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/home")
public class HomeController {

    private final NotesService notesService;
    private final CredentialService credentialService;
    private final UserService userService;

    public HomeController(NotesService notesService, CredentialService credentialService, UserService userService) {
        this.notesService = notesService;
        this.credentialService = credentialService;
        this.userService = userService;
    }


    @GetMapping()
    public String homeView(Model model, Authentication auth) {
        var user = userService.getUser(auth.getName());
        model.addAttribute("notes", this.notesService.getNotes(user.getUserId()));
        model.addAttribute("credentials", this.credentialService.getCredentials(user.getUserId()));
        return "home";
    }

}
