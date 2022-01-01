package com.ingressocom.portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ingressocom.portal.model.State;
import com.ingressocom.portal.repository.StateRepository;

@Component
public class StateService {

    @Autowired StateRepository stateRepository;
    
    public State findById(Long id) {
        return stateRepository.findById(id).get();
    }
    
    public Iterable<State> findAll(){
        return stateRepository.findAll();
    }

}
