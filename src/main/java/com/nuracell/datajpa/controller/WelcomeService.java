package com.nuracell.datajpa.controller;

import org.springframework.stereotype.Service;

@Service
public class WelcomeService {

    public String getWelcomeMessage(String message) {
        return String.format("Welcome %s!", message);
    }
}
