package com.encuesta.encuestabackend.services;

import com.encuesta.encuestabackend.entities.UserEntity;
import com.encuesta.encuestabackend.models.request.UserRegisterRequestModel;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{

    UserDetails loadUserByUsername(String email);
    UserEntity getUser(String email);
    UserEntity createUser(UserRegisterRequestModel user);
}
