package com.ingressocom.portal.configuration;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.ingressocom.portal.model.Cinema;
import com.ingressocom.portal.model.Movie;
import com.ingressocom.portal.model.Screen;
import com.ingressocom.portal.model.Showing;
import com.ingressocom.portal.model.State;
import com.ingressocom.portal.repository.CinemaRepository;
import com.ingressocom.portal.repository.MovieRepository;
import com.ingressocom.portal.repository.ShowingRepository;
import com.ingressocom.portal.repository.StateRepository;
import com.ingressocom.portal.service.LoaderService;


@Component
public class Loader {

    @Autowired LoaderService loaderService;

    

}


