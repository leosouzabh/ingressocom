package com.ingressocom.portal.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ingressocom.portal.model.State;
import com.ingressocom.portal.pojo.StateForm;
import com.ingressocom.portal.service.MovieService;
import com.ingressocom.portal.service.StateService;


@Controller
@RequestMapping("home")
public class HomeController extends BaseController {
    
    @Autowired MovieService movieService;
    @Autowired StateService stateService;
    
    @Autowired(required=true) HttpServletRequest request;

    @GetMapping("/index")
    String index(Model model) {
        
        if ( getStateFromSession() == null ) {
            model.addAttribute("stateForm", new StateForm());
            model.addAttribute("stateList", stateService.findAll());            
            return "home/index";
        
        } else {
            model.addAttribute("movies", movieService.findAll());
            return "home/movies";   
        }        
    }
    
    @PostMapping("/state")
    String state(Model model, 
            @ModelAttribute(value="stateForm") StateForm stateForm) {
        
        State state = stateService.findById(stateForm.getStateId());
        super.setStateToSession(state);
        
        model.addAttribute("movies", movieService.findAll());
        
        return "home/movies";
    }
    
}


