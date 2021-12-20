package com.ingressocom.portal.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.ingressocom.portal.model.Cinema;
import com.ingressocom.portal.model.Movie;
import com.ingressocom.portal.model.Screen;
import com.ingressocom.portal.model.Showing;
import com.ingressocom.portal.repository.CinemaRepository;
import com.ingressocom.portal.repository.MovieRepository;
import com.ingressocom.portal.repository.ShowingRepository;


@Component
public class Loader {

    @Autowired MovieRepository movieRepository;
    @Autowired CinemaRepository cinemaRepository;
    @Autowired ShowingRepository showingRepository;
    
    @Bean
    public CommandLineRunner loadData() {
        return (args) -> {
            
            List<Cinema> cinemas = saveCinema("ODEON Naas","ODEON Newsbridge");
            
            saveData("Clifford: O Gigante Cão Vermelho", "a.jpg", cinemas, "01");
            saveData("Homem-Aranha: Sem Volta para Casa", "b.jpg", cinemas, "02", "03", "04");
            saveData("Resident Evil: Bem-vindo a Raccoon City", "c.jpg", cinemas, "04");
            saveData("Eternos", "d.jpg", cinemas, "06");
            
        };
    }

    private List<Cinema> saveCinema(String... names) {
        List<Cinema> returnList = new ArrayList<Cinema>();
        for (String name : names) {
            Cinema cinema = new Cinema();
            cinema.setName(name);
            
            cinema.setScreens(new HashSet<Screen>());
            cinema.getScreens().add( new Screen(cinema, "Screen 01", 15, 25) );
            cinema.getScreens().add( new Screen(cinema, "Screen 02", 20, 20) );
            cinema.getScreens().add( new Screen(cinema, "Screen 03", 10, 20) );
            cinema.getScreens().add( new Screen(cinema, "Screen 04", 10, 20) );
            cinema.getScreens().add( new Screen(cinema, "Screen 05", 15, 20) );
            cinema.getScreens().add( new Screen(cinema, "Screen 06", 10, 25) );
            
            cinemaRepository.save(cinema);
            
            returnList.add(cinema);
        }        
        
        return returnList;
    }

    private void saveData(String name, String image, List<Cinema> cinemas, String... screenNames) {
        Movie movie = new Movie(name, image);
        movieRepository.save(movie);
        
        
        for ( Cinema cinema : cinemas ) {
            for (String time : Arrays.asList("12:00", "15:00", "18:00", "21:00")) {
                for ( String screenIdx : screenNames ) {
                    //Finde the screen By name
                    Screen screen = cinema.getScreens()
                            .stream()
                            .filter( x -> x.getName().contains(screenIdx) )
                            .collect(Collectors.toList())
                            .iterator().next();
                    showingRepository.save(new Showing(screen, movie, time));                    
                }
            }
        }
        
    }

}


