package com.ingressocom.portal.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ingressocom.portal.exception.MovieNotFoundException;
import com.ingressocom.portal.model.Cinema;
import com.ingressocom.portal.model.Movie;
import com.ingressocom.portal.model.Screen;
import com.ingressocom.portal.model.Showing;
import com.ingressocom.portal.service.CheckoutService;
import com.ingressocom.portal.service.MovieService;
import com.ingressocom.portal.service.ShowingService;


@Controller
@RequestMapping("checkout")
public class CheckoutController {
    
    @Autowired MovieService movieService;
    @Autowired CheckoutService checkoutService;
    @Autowired ShowingService showingService;
    
    @GetMapping({"/", ""})
    public String index(
            @RequestParam(value = "movie", required = false) Long id,
            @RequestParam(value = "date", required = false) String activeDay,
            Model model) {
        
        Optional<Movie> movieOptional = movieService.findById(id);
        if ( movieOptional.isPresent() ) {
            Movie movie = movieOptional.get();
            model.addAttribute("movie", movie);
            
            model.addAttribute("dates", checkoutService.getListDates(activeDay));
            
            Map<Cinema, Map<Screen, List<Showing>>> result = checkoutService.getScreenAndTimes(movie);
            model.addAttribute("screenAndTimes", result);
            
            return "checkout/cinema";
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
        model.addAttribute("showing", showing);

        return "checkout/seat";
    }
    
}
