package com.encuesta.encuestabackend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import com.encuesta.encuestabackend.models.request.UserLoginRequestModel;
import com.encuesta.encuestabackend.models.request.UserRegisterRequestModel;
import com.encuesta.encuestabackend.repository.UserRepository;
import com.encuesta.encuestabackend.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class LoginTests {
    
    private static final String API_LOGIN_URL= "/user/login";

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void cleanup(){
        userRepository.deleteAll();
    }

    @Test
    public void postLogin_sinCredenciales_retornaForbidden(){
        ResponseEntity<Object> response = login(null, Object.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void postLogin_conCredencialesIncorrectas_retornaUnathorized(){

        UserRegisterRequestModel user = TestUtil.createValidUser();
        userService.createUser(user);

        UserLoginRequestModel model = new UserLoginRequestModel();
        model.setEmail("asd@gmail.com");
        model.setPassword("password");

        ResponseEntity<Object> response = login(model, Object.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void postLogin_conCredencialesCorrectas_retornaOk(){

        UserRegisterRequestModel user = TestUtil.createValidUser();
        userService.createUser(user);
        
        UserLoginRequestModel model = new UserLoginRequestModel();
        model.setEmail(user.getEmail());
        model.setPassword(user.getPassword());

        ResponseEntity<Object> response = login(model, Object.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void postLogin_conCredencialesCorrectas_retornaAuthToken(){

        UserRegisterRequestModel user = TestUtil.createValidUser();
        userService.createUser(user);
        
        UserLoginRequestModel model = new UserLoginRequestModel();
        model.setEmail(user.getEmail());
        model.setPassword(user.getPassword());

        ResponseEntity<Map<String,String>> response = 
        login(model, new ParameterizedTypeReference<Map<String,String>>(){});

        Map<String,String> body = response.getBody();

        String token = body.get("token");

        assertTrue(token.contains("Bearer"));
    }

    public <T> ResponseEntity<T> login(UserLoginRequestModel data,Class<T> responseType){
        return testRestTemplate.postForEntity(API_LOGIN_URL, data, responseType);
    }

    public <T> ResponseEntity<T> login(UserLoginRequestModel data,ParameterizedTypeReference<T> responseType){
        HttpEntity<UserLoginRequestModel> entity = new HttpEntity<UserLoginRequestModel>(data,new HttpHeaders());
        return testRestTemplate.exchange(API_LOGIN_URL, HttpMethod.POST, entity, responseType);
    }
}
