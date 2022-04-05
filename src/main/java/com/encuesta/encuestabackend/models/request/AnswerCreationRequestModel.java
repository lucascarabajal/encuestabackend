package com.encuesta.encuestabackend.models.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AnswerCreationRequestModel {
    
    @NotBlank
    private String content;
}
