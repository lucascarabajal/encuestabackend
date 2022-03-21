package com.encuesta.encuestabackend.models.request;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.encuesta.encuestabackend.annotations.UniqueEmail;

import lombok.Data;

@Data
public class UserRegisterRequestModel {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    @UniqueEmail
    private String email;

    @NotBlank
    @Size(min= 8, max=40)
    private String password;
    
}
