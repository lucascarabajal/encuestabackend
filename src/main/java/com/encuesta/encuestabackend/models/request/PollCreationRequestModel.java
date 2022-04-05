package com.encuesta.encuestabackend.models.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PollCreationRequestModel {
    
    @NotBlank
    private String content;

    @NotNull
    private boolean isOpened;

    @Valid
    @NotEmpty
    @Size(min = 1, max = 30)
    private List<QuestionCreationRequestModel> questions;

}
