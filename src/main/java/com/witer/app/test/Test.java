package com.witer.app.test;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class Test {

    @GetMapping("/")
    public String home() {
        return "home";
    }

}
