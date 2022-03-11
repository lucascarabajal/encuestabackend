package com.encuesta.encuestabackend.services;

import com.encuesta.encuestabackend.entities.UserEntity;
import com.encuesta.encuestabackend.models.request.UserRegisterRequestModel;

public interface UserService {
    public UserEntity createUser(UserRegisterRequestModel user);
}
