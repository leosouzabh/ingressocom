package com.ingressocom.portal.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ingressocom.portal.exception.MovieNotFoundException;
import com.ingressocom.portal.model.Movie;
import com.ingressocom.portal.service.CheckoutService;
import com.ingressocom.portal.service.MovieService;


@Controller
@RequestMapping("checkout")
public class CheckoutController {
    
    @Autowired MovieService movieService;
    @Autowired CheckoutService checkoutService;
    
    @GetMapping({"/", ""})
    public String index(
            @RequestParam(value = "movie", required = false) Long id,
            @RequestParam(value = "date", required = false) String activeDay,
            Model model) {
        
        Optional<Movie> movie = movieService.findById(id);
        if ( movie.isPresent() ) {            
            model.addAttribute("dates", checkoutService.getListDates(activeDay));
            model.addAttribute("movie", movie.get());
            return "checkout/cinema";
        } else {
            throw new MovieNotFoundException();
        }
    }
    
}
