package com.ingressocom.portal.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ingressocom.portal.model.Showing;
import com.ingressocom.portal.model.Ticket;
import com.ingressocom.portal.repository.TicketRepository;

@Component
public class TicketService {

    @Autowired TicketRepository ticketRepository;
    
    public Integer findTotalByShowing(Showing showing) {
        return ticketRepository.findTotalByShowing(showing);
    }
    
    public List<String> findBookedSeatsByShowing(Showing showing) {
        return ticketRepository.findSeatByShowing(showing);
    }

    public void save(Ticket ticket) {
        ticketRepository.save(ticket);
    }

}
