package com.example.springsecuritylearning;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    @GetMapping("loginForm")
    public String loginForm(){
        return "loginForm";
    }

}
