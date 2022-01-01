package com.ingressocom.portal.service;

import java.util.Optional;
import org.springframework.stereotype.Component;
import com.ingressocom.portal.model.Movie;
import com.ingressocom.portal.repository.MovieRepository;

@Component
public class MovieService {

    private MovieRepository movieRepository;
    
    public MovieService(MovieRepository movieRepository) {
        super();
        this.movieRepository = movieRepository;
    }

    public Iterable<Movie> findAll(){
        return movieRepository.findAll();
    }
    
    public Optional<Movie> findById(Long id) {
        return movieRepository.findById(id);
    }

    public Optional<Movie> findByCode(String code) {
        return movieRepository.findByCode(code);
    }

}
