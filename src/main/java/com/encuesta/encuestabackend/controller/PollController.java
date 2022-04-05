package com.encuesta.encuestabackend.controller;

import javax.validation.Valid;

import com.encuesta.encuestabackend.models.request.PollCreationRequestModel;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/polls")
public class PollController {

    @PostMapping
    public String createPoll(@RequestBody @Valid PollCreationRequestModel pollCreationRequestModel,Authentication authentication){
        System.out.println(pollCreationRequestModel);
        return authentication.getPrincipal().toString();
    }
    
}
