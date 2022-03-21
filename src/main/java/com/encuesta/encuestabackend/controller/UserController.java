package com.encuesta.encuestabackend.controller;

import javax.validation.Valid;

import com.encuesta.encuestabackend.entities.UserEntity;
import com.encuesta.encuestabackend.models.request.UserRegisterRequestModel;
import com.encuesta.encuestabackend.models.responses.UserRest;
import com.encuesta.encuestabackend.services.UserService;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<UserRest> createUser(@RequestBody @Valid UserRegisterRequestModel userModel) {        
        UserEntity user = userService.createUser(userModel);

        UserRest userRest = new UserRest();

        BeanUtils.copyProperties(user,userRest);

        return ResponseEntity.status(HttpStatus.CREATED).body(userRest);
    }    

    @GetMapping
    public UserRest getUser(Authentication authentication){
        String userEmail = authentication.getPrincipal().toString();

        UserEntity user = userService.getUser(userEmail);

        UserRest userRest = new UserRest();

        BeanUtils.copyProperties(user,userRest);

        return userRest;
    }

}
