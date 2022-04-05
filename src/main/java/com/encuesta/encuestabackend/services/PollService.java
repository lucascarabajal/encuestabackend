package com.encuesta.encuestabackend.services;

import com.encuesta.encuestabackend.models.request.PollCreationRequestModel;

public interface PollService {
    String createPoll(PollCreationRequestModel model, String email);
}
