package com.encuesta.encuestabackend.services;

import java.util.ArrayList;

import com.encuesta.encuestabackend.entities.UserEntity;
import com.encuesta.encuestabackend.models.request.UserRegisterRequestModel;
import com.encuesta.encuestabackend.repository.UserRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository; 

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserEntity createUser(UserRegisterRequestModel user) {
        UserEntity userEntity = new UserEntity();

        BeanUtils.copyProperties(user, userEntity);

        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return userRepository.save(userEntity);
        
    }

    @Override
    public UserDetails loadUserByUsername(String email)throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email);

        if(user == null){
            throw new UsernameNotFoundException(email);
        }

        return new User(user.getEmail(), user.getEncryptedPassword(), new ArrayList<>());
    }
    
}
