package com.encuesta.encuestabackend.models.request;

import lombok.Data;

@Data
public class UserRegisterRequestModel {

    private String name;

    private String email;
    
    private String password;
    
}
