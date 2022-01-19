package com.esoe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class appController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

}
