package com.encuesta.encuestabackend.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/polls")
public class PollController {

    @PostMapping
    public String createPoll(Authentication authentication){
        return authentication.getPrincipal().toString();
    }
    
}
