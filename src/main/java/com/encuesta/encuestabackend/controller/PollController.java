package com.encuesta.encuestabackend.controller;

import javax.validation.Valid;

import com.encuesta.encuestabackend.models.request.PollCreationRequestModel;
import com.encuesta.encuestabackend.models.responses.CreatedPollRest;
import com.encuesta.encuestabackend.services.PollService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/polls")
public class PollController {

    @Autowired
    PollService pollService;

    @PostMapping
    public CreatedPollRest createPoll(@RequestBody @Valid PollCreationRequestModel model,Authentication authentication){
        String pollId = pollService.createPoll(model, authentication.getPrincipal().toString());
        return new CreatedPollRest(pollId);        
    }
    
}
