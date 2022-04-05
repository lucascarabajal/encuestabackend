package com.encuesta.encuestabackend.services;

import java.util.UUID;

import com.encuesta.encuestabackend.entities.AnswerEntity;
import com.encuesta.encuestabackend.entities.PollEntity;
import com.encuesta.encuestabackend.entities.QuestionEntity;
import com.encuesta.encuestabackend.entities.UserEntity;
import com.encuesta.encuestabackend.models.request.PollCreationRequestModel;
import com.encuesta.encuestabackend.repository.PollRepository;
import com.encuesta.encuestabackend.repository.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PollServiceImpl implements PollService{

    private final PollRepository pollRepository;
    private final UserRepository userRepository;

    @Override
    public String createPoll(PollCreationRequestModel model, String email) {
        
        UserEntity user = userRepository.findByEmail(email);

        ModelMapper mapper = new ModelMapper();

        PollEntity pollEntity = mapper.map(model, PollEntity.class);

        pollEntity.setUser(user);
        pollEntity.setPollId(UUID.randomUUID().toString());

        for(QuestionEntity question: pollEntity.getQuestions()){
            question.setPoll(pollEntity);
            for(AnswerEntity answer: question.getAnswers()){
                answer.setQuestion(question);
            }
        }
        pollRepository.save(pollEntity);

        return pollEntity.getPollId();
    }
    
}
