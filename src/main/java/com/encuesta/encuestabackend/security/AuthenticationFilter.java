package com.encuesta.encuestabackend.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.encuesta.encuestabackend.models.request.UserLoginRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)throws AuthenticationException{

        try {

            UserLoginRequestModel userModel = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequestModel.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userModel.getEmail(), userModel.getPassword(), new ArrayList<>()));
            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

        String email = ((User)authentication.getPrincipal()).getUsername();

        //Generate Token
        String token = Jwts.builder().setSubject(email).setExpiration(new Date()).signWith(SignatureAlgorithm.HS512, "clavesecreta").compact();

        System.out.print(token);
    }

}
