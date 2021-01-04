package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping()
    public String loginView(@RequestParam(required = false, name = "signupSuccess") Boolean signupSuccess, Model model) {
        Map<String, Object> data = new HashMap<>();

        data.put("signupSuccess", signupSuccess);

        model.addAllAttributes(data);
        return "login";
    }
}
