package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/notes")
public class NoteController {

    private final NotesService notesService;
    private final UserService userService;

    public NoteController(NotesService notesService, UserService userService) {
        this.notesService = notesService;
        this.userService = userService;
    }

    @PostMapping()
    public String addNote(Authentication auth, @ModelAttribute Note note, Model model) {
        var user = userService.getUser(auth.getName());
        note.setUserid(user.getUserId());
        notesService.createNote(note);
        model.addAttribute("activeTab","notes");
        return "redirect:/home";
    }
}
