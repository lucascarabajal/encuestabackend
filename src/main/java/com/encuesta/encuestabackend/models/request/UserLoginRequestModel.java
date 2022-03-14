package com.encuesta.encuestabackend.models.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserLoginRequestModel {
    
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
