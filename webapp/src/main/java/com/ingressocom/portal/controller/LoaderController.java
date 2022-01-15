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
import com.ingressocom.portal.service.LoaderService;
import com.ingressocom.portal.service.MovieService;
import com.ingressocom.portal.service.StateService;


@Controller
@RequestMapping("loader")
public class LoaderController extends BaseController {
    
    @Autowired LoaderService loaderService;

    @GetMapping({"", "/"})
    String index(Model model) {
        boolean loaded = loaderService.isDataLoaded();
        model.addAttribute("dataLoaded", loaded);
        return "loader/index";
    }

    @PostMapping("load")
    String load(Model model) {
        boolean loaded = loaderService.loadData();
        model.addAttribute("dataLoaded", loaded);
        return "loader/done";
    }
}


