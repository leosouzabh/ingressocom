package com.ingressocom.portal.controller;

import com.ingressocom.portal.service.LoaderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


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


