package com.encuesta.encuestabackend.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.encuesta.encuestabackend.annotations.UniqueEmail;
import com.encuesta.encuestabackend.entities.UserEntity;
import com.encuesta.encuestabackend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String>{

    @Autowired
    UserRepository userRepository;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        UserEntity user = userRepository.findByEmail(value);
        if (user== null){
            return true;
        }
        return false;
    }
    
}
