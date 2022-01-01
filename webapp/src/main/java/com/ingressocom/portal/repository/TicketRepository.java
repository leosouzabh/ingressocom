package com.ingressocom.portal.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ingressocom.portal.model.Showing;
import com.ingressocom.portal.model.Ticket;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {
 
    @Query("select count(t.seat) from Ticket t where t.showing = :showing")
    Integer findTotalByShowing(@Param("showing") Showing showing);
    
    @Query("select t.seat from Ticket t where t.showing = :showing")
    List<String>findSeatByShowing(@Param("showing") Showing showing);
    
    
}