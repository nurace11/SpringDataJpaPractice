package com.nuracell.datajpa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("hello")
    String hello(@RequestParam(name = "name", defaultValue = "World") String message){
        return String.format("Hello, %s", message);
    }
}
