package com.encuesta.encuestabackend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import com.encuesta.encuestabackend.entities.UserEntity;
import com.encuesta.encuestabackend.models.request.UserLoginRequestModel;
import com.encuesta.encuestabackend.models.request.UserRegisterRequestModel;
import com.encuesta.encuestabackend.models.responses.UserRest;
import com.encuesta.encuestabackend.models.responses.ValidationErrors;
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
import org.springframework.security.web.header.Header;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {

    private static final String API_URL = "/users";
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
    public void createUser_sinElCampoNombre_retornaBadRequest(){
        UserRegisterRequestModel user = TestUtil.createValidUser();
        user.setName(null);
        ResponseEntity<Object> response = register(user,Object.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void createUser_sinElPassword_retornaBadRequest(){
        UserRegisterRequestModel user = TestUtil.createValidUser();
        user.setPassword(null);
        ResponseEntity<Object> response = register(user,Object.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void createUser_sinElEmail_retornaBadRequest(){
        UserRegisterRequestModel user = TestUtil.createValidUser();
        user.setEmail(null);
        ResponseEntity<Object> response = register(user,Object.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void createUser_sinNingunDato_retornaBadRequest(){
        ResponseEntity<Object> response = register( new UserRegisterRequestModel(),Object.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void createUser_sinNingunDato_retornaErroresDeValidacion(){
        ResponseEntity<ValidationErrors> response = 
        register( new UserRegisterRequestModel(),ValidationErrors.class);
        Map<String,String> errors = response.getBody().getErrors();
        assertEquals(3, errors.size());
    }

    @Test
    public void createUser_sinNombre_retornaErrorDeValidacionNombre(){
        UserRegisterRequestModel user = TestUtil.createValidUser();
        user.setName(null);
        ResponseEntity<ValidationErrors> response = register( user,ValidationErrors.class);
        
        Map<String,String> errors = response.getBody().getErrors();
        assertTrue(errors.containsKey("name"));
    }

    @Test
    public void createUser_sinEmail_retornaErrorDeValidacionEmail(){
        UserRegisterRequestModel user = TestUtil.createValidUser();
        user.setEmail(null);
        ResponseEntity<ValidationErrors> response = register( user,ValidationErrors.class);
        
        Map<String,String> errors = response.getBody().getErrors();
        assertTrue(errors.containsKey("email"));
    }

    @Test
    public void createUser_sinPassword_retornaErrorDeValidacionPassword(){
        UserRegisterRequestModel user = TestUtil.createValidUser();
        user.setPassword(null);
        ResponseEntity<ValidationErrors> response = register( user,ValidationErrors.class);
        
        Map<String,String> errors = response.getBody().getErrors();
        assertTrue(errors.containsKey("password"));
    }

    @Test
    public void createUser_conUsuarioValido_retornaOK(){
        UserRegisterRequestModel user = TestUtil.createValidUser();        
        ResponseEntity<UserRest> response = register( user,UserRest.class);
        assertEquals(HttpStatus.CREATED ,response.getStatusCode());
    }

    @Test
    public void createUser_conUsuarioValido_retornaUserRest(){
        UserRegisterRequestModel user = TestUtil.createValidUser();        
        ResponseEntity<UserRest> response = register( user,UserRest.class);
        assertEquals(response.getBody().getName(), user.getName());
    }

    @Test
    public void createUser_conUsuarioValido_guardaElUsuarioEnBD(){
        UserRegisterRequestModel user = TestUtil.createValidUser();        
        ResponseEntity<UserRest> response = register( user,UserRest.class);
        UserEntity userDB = userRepository.findById(response.getBody().getId());
        assertNotNull(userDB);
    }

    @Test
    public void createUser_conUsuarioValido_guardaPasswordHash(){
        UserRegisterRequestModel user = TestUtil.createValidUser();        
        ResponseEntity<UserRest> response = register( user,UserRest.class);
        UserEntity userDB = userRepository.findById(response.getBody().getId());
        assertNotEquals(user.getPassword(), userDB.getEncryptedPassword());;
    }

    @Test
    public void createUser_conUsuarioValidoConEmailExistente_RetornaBadRequest(){
        UserRegisterRequestModel user = TestUtil.createValidUser();        
        register(user,UserRest.class);
        ResponseEntity<UserRest> response2 = register(user,UserRest.class);
        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
    }

    @Test
    public void createUser_conUsuarioValidoConEmailExistente_retornaErrorDeValidacionEmail(){
        UserRegisterRequestModel user = TestUtil.createValidUser();        
        register(user,UserRest.class);
        ResponseEntity<ValidationErrors> response2 = register( user,ValidationErrors.class);
        
        Map<String,String> errors = response2.getBody().getErrors();
        assertTrue(errors.containsKey("email"));
    }

    @Test
    public void getUser_sinToken_retornaForbidden(){
        ResponseEntity<Object> response = getUser(null, new ParameterizedTypeReference<Object>(){});
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void getUser_conToken_retornaOK(){
        UserRegisterRequestModel user = TestUtil.createValidUser();
        userService.createUser(user);
        
        UserLoginRequestModel model = new UserLoginRequestModel();
        model.setEmail(user.getEmail());
        model.setPassword(user.getPassword());

        ResponseEntity<Map<String,String>> responseLogin = 
        login(model, new ParameterizedTypeReference<Map<String,String>>(){});

        Map<String,String> body = responseLogin.getBody();
        String token = body.get("token").replace("Bearer ", "");

        ResponseEntity<UserRest> response = getUser(token, new ParameterizedTypeReference<UserRest>(){});
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getUser_conToken_retornaUserRest(){
        UserRegisterRequestModel user = TestUtil.createValidUser();
        userService.createUser(user);
        
        UserLoginRequestModel model = new UserLoginRequestModel();
        model.setEmail(user.getEmail());
        model.setPassword(user.getPassword());

        ResponseEntity<Map<String,String>> responseLogin = 
        login(model, new ParameterizedTypeReference<Map<String,String>>(){});

        Map<String,String> body = responseLogin.getBody();
        String token = body.get("token").replace("Bearer ", "");

        ResponseEntity<UserRest> response = getUser(token, new ParameterizedTypeReference<UserRest>(){});
        assertEquals(response.getBody().getName(), user.getName());
    }

    public <T> ResponseEntity<T> register(UserRegisterRequestModel data,Class<T> responseType){
        return testRestTemplate.postForEntity(API_URL, data, responseType);
    }

    public <T> ResponseEntity<T> getUser(String token, ParameterizedTypeReference<T> responseType){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Object> entity = new HttpEntity<Object>(null,headers);
        return testRestTemplate.exchange(API_URL, HttpMethod.GET, entity, responseType);
    }

    public <T> ResponseEntity<T> login(UserLoginRequestModel data,ParameterizedTypeReference<T> responseType){
        HttpEntity<UserLoginRequestModel> entity = new HttpEntity<UserLoginRequestModel>(data,new HttpHeaders());
        return testRestTemplate.exchange(API_LOGIN_URL, HttpMethod.POST, entity, responseType);
    }
}
