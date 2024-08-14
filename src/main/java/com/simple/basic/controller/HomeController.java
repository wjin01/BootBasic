package com.simple.basic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //일반 컨트롤러
public class HomeController {

    @GetMapping("/")
    //@ResponseBody
    public String home() {
        return "hello"; //
    }
}
