package com.esoe.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class indexController {

    @PostMapping("/index")
    public String index() {
        return "index";
    }

}

