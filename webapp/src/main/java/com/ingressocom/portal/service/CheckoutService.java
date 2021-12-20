package com.ingressocom.portal.service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ingressocom.portal.model.Cinema;
import com.ingressocom.portal.model.Movie;
import com.ingressocom.portal.model.Screen;
import com.ingressocom.portal.model.Showing;
import com.ingressocom.portal.pojo.DayWeekListWrapper;
import com.ingressocom.portal.repository.ShowingRepository;

@Component
public class CheckoutService {

    @Autowired ShowingRepository showingRepository;
    
    public DayWeekListWrapper getListDates(String activeDay) {
        
        DayWeekListWrapper listReturn = new DayWeekListWrapper(activeDay);
        
        Calendar now = new GregorianCalendar();
        listReturn.addDate(now.getTime());

        for ( int x=1; x<=7; x++ ) {
            now.add(Calendar.DAY_OF_YEAR, 1);
            listReturn.addDate(now.getTime());
        }
        
        return listReturn;
    }
    
    public Map<Cinema, Map<Screen, List<Showing>>> getScreenAndTimes(Movie movie) {
        List<Showing> showing = showingRepository.findByMovie(movie); 
        
        
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
    

}
