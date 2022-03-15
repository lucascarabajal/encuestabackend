package com.encuesta.encuestabackend.security;

import com.encuesta.encuestabackend.SpringApplicationContext;


public class SecurityConstants {
    
    public static final long EXPIRATION_DATE = 864000000; // 10 días
    public static final String LOGIN_URL = "/user/login";
    public static final String TOKEN_PREFIX = "Bearer ";

    public static String getTokenSecret(){
        AppProperties appProperties = (AppProperties)SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
}
