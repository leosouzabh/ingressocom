package com.ingressocom.portal.service;

import java.util.List;

import com.ingressocom.portal.pojo.MovieLoader;

public interface LoaderDataComponent {
    
    List<String[]> getStates();
    List<String> getCinemas();
    List<MovieLoader> getMovies();

}
