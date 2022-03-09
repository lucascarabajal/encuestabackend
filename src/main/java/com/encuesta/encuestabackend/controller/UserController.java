package com.encuesta.encuestabackend.controller;

import com.encuesta.encuestabackend.models.request.UserRegisterRequestModel;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping()
    public String createUser(@RequestBody UserRegisterRequestModel userModel) {        
        return "Create";
    }
    

    
}
