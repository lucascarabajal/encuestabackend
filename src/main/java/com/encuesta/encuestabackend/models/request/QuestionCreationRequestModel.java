package com.encuesta.encuestabackend.models.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.encuesta.encuestabackend.annotations.ValueOfEnum;
import com.encuesta.enums.QuestionType;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
public class QuestionCreationRequestModel {
    
    @NotBlank
    private String content;

    @NotNull
    @Range(min = 1, max = 30)
    private int questionOrder;

    @ValueOfEnum(enumClass = QuestionType.class)
    private String type;

    @Valid
    @NotEmpty
    @Size(min = 1, max = 10)
    private List<AnswerCreationRequestModel> answers;
}
