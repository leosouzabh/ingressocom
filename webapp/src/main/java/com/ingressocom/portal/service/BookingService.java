package com.ingressocom.portal.service;

import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.ingressocom.portal.exception.SeatAlreadyLocked;
import com.ingressocom.portal.model.Booking;
import com.ingressocom.portal.model.Showing;
import com.ingressocom.portal.pojo.ExternalBookingInput;
import com.ingressocom.portal.pojo.ExternalBookingOutput;
import com.ingressocom.portal.repository.BookingRepository;

@Component
public class BookingService {

    @Autowired BookingRepository bookingRepository;
    
    @Autowired RestTemplate restTemplate;
    
    @Value("${EXTERNAL_BOOKING_ENDPOINT}")
    String externalBookingEndpoint;
    
    public void lockSeat(Showing showing, String seat, String httpSessionId) throws SeatAlreadyLocked {
        createBooking(showing, seat, httpSessionId);
        callExternalBookingService(showing, seat, httpSessionId);
    }

    private void callExternalBookingService(Showing showing, String seat, String httpSessionId) {
        ExternalBookingInput input = new ExternalBookingInput();
        
        input.setCinema(showing.getScreen().getCinema().getName());
        input.setScreen(showing.getScreen().getName());
        input.setMovie(showing.getMovie().getName());
        input.setDatetime(LocalDateTime.now());
        input.setHttpSessionId(httpSessionId);
        
        ResponseEntity<ExternalBookingOutput> result = restTemplate.postForEntity(externalBookingEndpoint, input, ExternalBookingOutput.class);

        System.out.println(result);
    }

    private void createBooking(Showing showing, String seat, String httpSessionId) throws SeatAlreadyLocked {
        try {
            Booking booking = new Booking();
            booking.setShowing(showing);        
            booking.setSeat(seat);        
            booking.setDatetime(LocalDateTime.now());
            booking.setHttpSessionId(httpSessionId);
            bookingRepository.save(booking);
        } catch (DataIntegrityViolationException e) {
            throw new SeatAlreadyLocked();
        }
    }

    public List<String> findBookedSeatsByShowing(Showing showing) {
        return bookingRepository.findBookedSeatsByShowing(showing);
    }

    @Transactional 
    public long cleanPreviousSelectedSeats(String httpSessionId) {
        return bookingRepository.deleteByHttpSessionId(httpSessionId);
    }
    
    

}
