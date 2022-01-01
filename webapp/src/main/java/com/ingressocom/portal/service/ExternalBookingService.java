package com.ingressocom.portal.service;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ingressocom.portal.model.Booking;
import com.ingressocom.portal.model.Showing;
import com.ingressocom.portal.repository.BookingRepository;

@Component
public class ExternalBookingService {

    @Autowired BookingRepository bookingRepository;
    
    public void lockSeat(Showing showing, String seat) {
        callExternalBookingService(showing, seat);
        createBooking(showing, seat);        
    }

    private void callExternalBookingService(Showing showing, String seat) {
        // TODO Auto-generated method stub
    }

    private void createBooking(Showing showing, String seat) {
        Booking booking = new Booking();
        booking.setShowing(showing);        
        booking.setSeat(seat);        
        booking.setDatetime(LocalDateTime.now());
        bookingRepository.save(booking);
    }
    
    

}
