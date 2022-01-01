package com.ingressocom.portal.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ingressocom.portal.model.Booking;
import com.ingressocom.portal.model.Showing;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {

    @Query("select b.seat from Booking b where b.showing = :showing")
    List<String> findBookedSeatsByShowing(@Param("showing") Showing showing);
    
    long deleteByHttpSessionId(String httpSessionId);
 
    
}