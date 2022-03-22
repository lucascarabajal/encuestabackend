package com.encuesta.encuestabackend;

import java.util.Random;

import com.encuesta.encuestabackend.models.request.UserRegisterRequestModel;

public class TestUtil {
    public static UserRegisterRequestModel createValidUser(){
        UserRegisterRequestModel user = new UserRegisterRequestModel();
        user.setEmail(generateRamdomString(8)+"@gmail.com");
        user.setName(generateRamdomString(8));
        user.setPassword(generateRamdomString(8));
        return user;
    }

    public static String generateRamdomString(int len){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWQYZabcdefghijklmnopqrstuvwxyz";

        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(len);

        for(int i =0; i<len; i++){
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }

        return sb.toString();
    }
}
