package com.encuesta.encuestabackend.controller;

import javax.validation.Valid;

import com.encuesta.encuestabackend.entities.UserEntity;
import com.encuesta.encuestabackend.models.request.UserRegisterRequestModel;
import com.encuesta.encuestabackend.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping()
    public UserEntity createUser(@RequestBody @Valid UserRegisterRequestModel userModel) {        
        UserEntity user = userService.createUser(userModel);
        return user;
    }
    


    
}
