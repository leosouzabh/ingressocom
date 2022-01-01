package com.ingressocom.portal.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import com.ingressocom.portal.exception.MovieNotFoundException;
import com.ingressocom.portal.model.Cinema;
import com.ingressocom.portal.model.Movie;
import com.ingressocom.portal.model.Screen;
import com.ingressocom.portal.model.Showing;
import com.ingressocom.portal.model.State;
import com.ingressocom.portal.service.BookingService;
import com.ingressocom.portal.service.CheckoutService;
import com.ingressocom.portal.service.MovieService;
import com.ingressocom.portal.service.ShowingService;


@Controller
@RequestMapping({"movie/{movie}", "movie/{movie}/{date}"})
public class MovieController extends BaseController {
    
    @Autowired MovieService movieService;
    @Autowired CheckoutService checkoutService;
    @Autowired ShowingService showingService;
    @Autowired BookingService bookingService;
        
    private DateTimeFormatter dateFormatUrl = DateTimeFormatter.ofPattern("yyyy-MM-dd");    
    
    @GetMapping({"/", ""})
    public String index(
            @PathVariable(value = "movie", required = true) String code,
            @PathVariable(value = "date", required = false) String activeDateStr,
            Model model) throws ParseException {
        
        State stateSession = getStateFromSession();
        
        LocalDate activeDay = LocalDate.now();
        if (!StringUtils.isEmpty(activeDateStr)) {
            activeDay =  LocalDate.parse(activeDateStr, dateFormatUrl); 
        }  

        Optional<Movie> movieOptional = movieService.findByCode(code);
        if ( movieOptional.isPresent() ) {
            Movie movie = movieOptional.get();
            
            model.addAttribute("movie", movie);
            model.addAttribute("dates", checkoutService.getListDates(activeDay));
            
            Map<Cinema, Map<Screen, List<Showing>>> result = checkoutService.getScreenAndTimes(movie, activeDay, stateSession);
            model.addAttribute("screenAndTimes", result);
            
            return "movie/cinema";
        } else {
            throw new MovieNotFoundException();
        }
    }
    
    @GetMapping({"/seat"})
    public String seat(
            @RequestParam(value = "showing", required = false) Long id,
            @RequestParam(value = "date", required = false) String date,
            Model model) {
        
        Showing showing = showingService.findById(id);
        String httpSessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        
        bookingService.cleanPreviousSelectedSeats(httpSessionId);
        
        model.addAttribute("showing", showing);
        model.addAttribute("unavailableSeats", checkoutService.getBookedSeats(showing));

        return "movie/seat";
    }
    
}
