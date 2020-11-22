package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller()
@RequestMapping()
public class NoteController {

    private final NotesService notesService;
    private final UserService userService;

    public NoteController(NotesService notesService, UserService userService) {
        this.notesService = notesService;
        this.userService = userService;
    }

    @PostMapping("/note-save")
    public String addNote(
            Authentication auth,
            @ModelAttribute Note note,
            RedirectAttributes redirectAttributes) {

        var user = userService.getUser(auth.getName());
        note.setUserid(user.getUserId());

        notesService.createNote(note);
        redirectAttributes.addAttribute("activeTab", "notes");
        return "redirect:/home";
    }

    @PostMapping("/note-delete")
    public String deleteNote(@RequestParam Integer noteid) {
        notesService.deleteNote(noteid);
        return "redirect:/home";
    }
}
