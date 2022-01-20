package com.ingressocom.portal.service;

import java.util.Arrays;
import java.util.List;

import com.ingressocom.portal.pojo.MovieLoader;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("aws")
public class LoaderDataAwsService implements LoaderDataComponent {

    @Override
    public List<String[]> getStates() {
        return Arrays.asList(
            new String[] {"Sacramento", "California", "CA"},
            new String[] {"Denver", "Colorado", "CO"},
            new String[] {"Manhattan", "New York", "NY"},
            new String[] {"Los Angeles", "California", "CA"},
            new String[] {"San Francisco", "California", "CA"},
            new String[] {"San Diego", "California", "CA"},
            new String[] {"California City", "California", "CA"},
            new String[] {"Orland", "Florida", "FL"},
            new String[] {"Atlanta", "Georgia", "GA"},
            new String[] {"Boston", "Massachusetts", "MA"},
            new String[] {"Carson City", "Nevada", "NV"},
            new String[] {"Trenton", "New Jersey", "NJ"},
            new String[] {"New York", "New York", "NY"},
            new String[] {"Washington, D.C.", "Washington", "WA"});
    }

    @Override
    public List<String> getCinemas() {
        return Arrays.asList(
            "AMC North" ,"AMC South", "AMC CityCenter", "AMC West", "AMC Shopping Center",  "AMC East",
            "Cineworld North", "Cineworld South", "Cineworld CityCenter", "Cineworld West", "Cineworld Shopping Center",  "Cineworld East",
            "Cineplex North", "Cineplex South", "Cineplex CityCenter", "Cineplex West", "Cineplex Shopping Center",  "Cineplex East",
            "Landmark North", "Landmark South", "Landmark CityCenter", "Landmark West", "Landmark Shopping Center",  "Landmark East",
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
