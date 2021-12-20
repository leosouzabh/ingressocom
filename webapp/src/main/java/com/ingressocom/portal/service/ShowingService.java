package com.ingressocom.portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ingressocom.portal.model.Showing;
import com.ingressocom.portal.repository.ShowingRepository;

@Component
public class ShowingService {

    @Autowired ShowingRepository showingRepository;
    
    public Showing findById(Long id) {
        return showingRepository.findById(id).get();
    }

}
