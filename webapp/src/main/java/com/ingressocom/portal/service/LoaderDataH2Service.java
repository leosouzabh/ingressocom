package com.ingressocom.portal.service;

import java.util.Arrays;
import java.util.List;

import com.ingressocom.portal.pojo.MovieLoader;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("h2")
public class LoaderDataH2Service implements LoaderDataComponent {

    @Override
    public List<String[]> getStates() {
        return Arrays.asList(
            new String[] {"Sacramento", "California", "CA"},
            new String[] {"Denver", "Colorado", "CO"},
            new String[] {"Manhattan", "New York", "NY"});
    }

    @Override
    public List<String> getCinemas() {
        return Arrays.asList(
            "AMC North" ,"AMC South", "AMC CityCenter", "AMC West", "AMC Shopping Center",  "AMC East",
            "CMX North", "CMX South", "CMX CityCenter", "CMX West", "CMX Shopping Center",  "CMX East"
        );
    }

    @Override
    public List<MovieLoader> getMovies() {
        return Arrays.asList(
            new MovieLoader("Clifford the Big Red Dog", "a.jpg", "01"),
            new MovieLoader("Spiderman: No Way Home", "b.jpg", "02", "03", "04", "05"),
            new MovieLoader("Resident Evil: Welcome to Raccoon City", "c.jpg", "06"),
            new MovieLoader("Ethernal", "d.jpg", "07"),
            new MovieLoader("Matrix", "k.jpg", "08")
        );
    }
    
}
