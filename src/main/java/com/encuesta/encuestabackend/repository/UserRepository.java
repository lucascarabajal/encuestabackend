package com.encuesta.encuestabackend.repository;

import com.encuesta.encuestabackend.entities.UserEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>{
    UserEntity findByEmail(String email);
    UserEntity findById(long id);
}
