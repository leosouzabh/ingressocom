package com.ingressocom.portal.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ingressocom.portal.model.Cinema;
import com.ingressocom.portal.model.Movie;
import com.ingressocom.portal.model.Screen;
import com.ingressocom.portal.model.Showing;
import com.ingressocom.portal.model.State;
import com.ingressocom.portal.pojo.DayWeekListWrapper;
import com.ingressocom.portal.repository.ShowingRepository;

@Component
public class CheckoutService {

    @Autowired ShowingRepository showingRepository;
    @Autowired TicketService ticketService;
    @Autowired BookingService bookingService;
    
    public DayWeekListWrapper getListDates(LocalDate activeDay) {
        
        DayWeekListWrapper listReturn = new DayWeekListWrapper(activeDay);
        
        LocalDate now = LocalDate.now();
        listReturn.addDate(now);

        for ( int x=1; x<=7; x++ ) {
            listReturn.addDate(now.plusDays(x));
        }
        
        return listReturn;
    }
    
    public Map<Cinema, Map<Screen, List<Showing>>> getScreenAndTimes(Movie movie, LocalDate date, State state) {
        List<Showing> showing = showingRepository.findByMovieAndDate(movie, date, state); 
        
        Function<Showing, Cinema> groupByCinema =  s -> s.getScreen().getCinema();
        Function<Showing, Screen> groupByScreen =  s -> s.getScreen();
        
        Map<Cinema, List<Showing>> collect = showing.stream()
                .collect(Collectors.groupingBy(groupByCinema));
        
        Map<Cinema, Map<Screen,List<Showing>>> result = new HashMap<>();
        collect.forEach( (cinema, listShowings) -> {
            result.put(cinema, listShowings.stream().collect(Collectors.groupingBy(groupByScreen)));
        });
        return result;
    }
    
    public Set<String> getBookedSeats(Showing showing) {
        List<String> bookedSeats = ticketService.findBookedSeatsByShowing(showing);
        List<String> ticketSeats = bookingService.findBookedSeatsByShowing(showing);
        Set<String> seats = new HashSet<String>();
        seats.addAll(bookedSeats);
        seats.addAll(ticketSeats);
        return seats;
    }

}
