package com.ingressocom.portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ingressocom.portal.model.Showing;
import com.ingressocom.portal.repository.ShowingRepository;

@Component
public class ShowingService {

    @Autowired ShowingRepository showingRepository;
    @Autowired TicketService ticketService;
    
    public Showing findById(Long id) {
        return showingRepository.findById(id).get();
    }

    public void checkAvailability(Showing showing) {
        Integer totalSeats = showing.getScreen().getTotalSeats();
        Integer ticketsIssued = ticketService.findTotalByShowing(showing);
        if ( ticketsIssued >= totalSeats ) {
            showing.setFull(true);
            showingRepository.save(showing);
        }
    }   
    

}
