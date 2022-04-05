package com.encuesta.encuestabackend.repository;

import com.encuesta.encuestabackend.entities.PollEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends CrudRepository<PollEntity, Long> {
    
}
