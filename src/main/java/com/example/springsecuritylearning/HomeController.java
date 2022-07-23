package com.example.springsecuritylearning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String index(){
        return "hello world!";
    }

    @GetMapping("/design")
    public String design() {return "design";}
}
