package com.nuracell.datajpa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    private WelcomeService welcomeService;

    public WelcomeController(WelcomeService welcomeService) {
        this.welcomeService = welcomeService;
    }

    @GetMapping("/welcome")// if name parameter in @RequestParam annotation is not defined, default name would be the name the parameter. In this case name = "message"
    public String welcome(@RequestParam( defaultValue = "Unknown") String message) {
        return welcomeService.getWelcomeMessage(message);
    }
}
