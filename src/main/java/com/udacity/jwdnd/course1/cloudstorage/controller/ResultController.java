package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller()
@RequestMapping("/result")
public class ResultController {

    @GetMapping()
    public String showResult(
            Authentication authentication,
            @RequestParam(required = false, name = "isSuccess") Boolean isSuccess,
            Model model
    ) {

        Map<String, Object> data = new HashMap<>();

        data.put("isSuccess", isSuccess);

        model.addAllAttributes(data);

        return "result";
    }
}
