package com.ingressocom.portal.controller;

import java.util.Arrays;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ingressocom.portal.service.MovieService;


@Controller
@RequestMapping("home")
public class HomeController {
    
    private MovieService movieService;
    
    public HomeController(MovieService movieService) {
        super();
        this.movieService = movieService;
    }

    @GetMapping("/index")
    String index(Model model) {
        model.addAttribute("movies", movieService.findAll());
        return "home/index";
    }
    
}
