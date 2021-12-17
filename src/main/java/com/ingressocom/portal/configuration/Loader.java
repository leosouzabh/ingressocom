package com.ingressocom.portal.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
            Iterator<Cinema> itCinema = cinemas.iterator();
            saveData("Clifford: O Gigante Cão Vermelho", "a.jpg", itCinema.next());
            saveData("Homem-Aranha: Sem Volta para Casa", "b.jpg", itCinema.next());
            saveData("Resident Evil: Bem-vindo a Raccoon City", "c.jpg", itCinema.next());
            saveData("Eternos", "d.jpg", itCinema.next());
            
        };
    }

    private List<Cinema> saveCinema(String... names) {
        List<Cinema> returnList = new ArrayList<Cinema>();
        for (String name : names) {
            Cinema cinema = new Cinema();
            cinema.setName(name);
            
            cinema.setScreens(new HashSet<Screen>());
            cinema.getScreens().add( new Screen(cinema, "Screen 01", 15, 25) );
            cinema.getScreens().add( new Screen(cinema, "Screen 02", 10, 20) );
            cinema.getScreens().add( new Screen(cinema, "Screen 03", 10, 20) );
            cinema.getScreens().add( new Screen(cinema, "Screen 04", 10, 20) );
            
            returnList.add(cinema);
        }        
        
        return returnList;
    }

    private void saveData(String name, String image, Cinema cinema) {
        Movie movie = new Movie(name, image);
        movieRepository.save(movie);
        
        
        Iterator<Screen> iterator = cinema.getScreens().iterator();
        for (String time : Arrays.asList("12:00", "15:00", "18:00", "21:00")) {
            showingRepository.save(new Showing(iterator.next(), time));
        }
        
    }

}


