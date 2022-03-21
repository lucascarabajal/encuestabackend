package com.encuesta.encuestabackend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.encuesta.encuestabackend.models.request.UserLoginRequestModel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class LoginTests {
    
    private static final String API_LOGIN_URL= "/user/login";

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void postLogin_sinCredenciales_retornaForbidden(){
        ResponseEntity<Object> response = login(null, Object.class);
        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    public <T> ResponseEntity<T> login(UserLoginRequestModel data,Class<T> responseType){
        return testRestTemplate.postForEntity(API_LOGIN_URL, data, responseType);
    }
}
