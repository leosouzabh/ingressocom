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


@Component
public class Loader {

    @Autowired MovieRepository movieRepository;
    @Autowired CinemaRepository cinemaRepository;
    @Autowired ShowingRepository showingRepository;
    @Autowired StateRepository stateRepository;
    
    //Fixed times 12:00, 15:00, 18:00, 21:00
    private int[] showingHours = new int[]{12, 15, 18, 21};
    //private int[] showingHours = new int[]{12, 15};
    
    private List<String[]> statesStr = Arrays.asList(
            new String[] {"Sacramento", "California", "CA"},
            new String[] {"Los Angeles", "California", "CA"},
            new String[] {"San Francisco", "California", "CA"},
            new String[] {"San Diego", "California", "CA"},
            new String[] {"California City", "California", "CA"},
            new String[] {"Denver", "Colorado", "CO"},
            new String[] {"1", "Florida", "FL"},
            new String[] {"1", "Georgia", "GA"},
            new String[] {"1", "Massachusetts", "MA"},
            new String[] {"1", "Nevada", "NV"},
            new String[] {"1", "New Jersey", "NJ"},
            new String[] {"1", "New York", "NY"},
            new String[] {"1", "Washington", "WA"});
    
    private List<String> cinemas = Arrays.asList(
            "AMC North"
            ,"AMC South", "AMC CityCenter", "AMC West", "AMC Shopping Center",  "AMC East",
            "Cineworld North", "Cineworld South", "Cineworld CityCenter", "Cineworld West", "Cineworld Shopping Center",  "Cineworld East",
            "Cineplex North", "Cineplex South", "Cineplex CityCenter", "Cineplex West", "Cineplex Shopping Center",  "Cineplex East",
            "Landmark North", "Landmark South", "Landmark CityCenter", "Landmark West", "Landmark Shopping Center",  "Landmark East",
            "CMX North", "CMX South", "CMX CityCenter", "CMX West", "CMX Shopping Center",  "CMX East"
            );
        
    
    @Bean
    public CommandLineRunner loadData() {
        return (args) -> {

            Map<String, State> usStates = saveStates();
            
            List<Cinema> cinemas = saveCinema(usStates);
            
            LocalDateTime exhibitionStart = LocalDateTime.now();
            LocalDateTime exhibitionEnd = LocalDateTime.now().plusDays(15);

            //addShowing("Spiderman: No Way Home", "b.jpg", cinemas, exhibitionStart, exhibitionEnd, "01");
            addShowing("Clifford the Big Red Dog", "a.jpg", cinemas, exhibitionStart, exhibitionEnd, "01");
            addShowing("Spiderman: No Way Home", "b.jpg", cinemas, exhibitionStart, exhibitionEnd, "02", "03", "04", "05");
            addShowing("Resident Evil: Welcome to Raccoon City", "c.jpg", cinemas, exhibitionStart, exhibitionEnd, "06");
            addShowing("Ethernal", "d.jpg", cinemas, exhibitionStart, exhibitionEnd, "07");            
            addShowing("Matrix", "k.jpg", cinemas, exhibitionStart, exhibitionEnd, "08");
        };
    }

    

    private Map<String, State> saveStates() {
        Map<String, State> mapReturn = new HashMap<String, State>();
        statesStr.stream()
            .map( stateArr -> new State(stateArr[1], stateArr[2]) )
            .collect(Collectors.toSet())
            .forEach( state -> {
                State stateObj = stateRepository.save(state);
                mapReturn.put(state.getAbbreviation(), stateObj);
            });
        return mapReturn;
    }

    private String sanitize(String in) {
        return in.toLowerCase().replaceAll("-", "").replaceAll(":", "").replaceAll("[^a-zA-Z0-9]", "-");
    }

    private List<Cinema> saveCinema(Map<String, State> states) {
        List<Cinema> returnList = new ArrayList<Cinema>();
        for ( String[] stateStr : statesStr ) {
            
            for (String cinemaName : cinemas) {
                Cinema cinema = new Cinema();
                cinema.setName(cinemaName);
                cinema.setCode(sanitize(cinemaName));
                cinema.setCity(stateStr[0]);
                cinema.setState(states.get(stateStr[2]));
    
                cinema.setScreens(new HashSet<Screen>());
                cinema.getScreens().add( new Screen(cinema, "Screen 01", 26, 30) );
                cinema.getScreens().add( new Screen(cinema, "Screen 02", 26, 30) );
                cinema.getScreens().add( new Screen(cinema, "Screen 03", 26, 30) );
                cinema.getScreens().add( new Screen(cinema, "Screen 04", 26, 30) );
                cinema.getScreens().add( new Screen(cinema, "Screen 05", 26, 30) );
                cinema.getScreens().add( new Screen(cinema, "Screen 06", 26, 25) );
                cinema.getScreens().add( new Screen(cinema, "Screen 07", 26, 25) );
                cinema.getScreens().add( new Screen(cinema, "Screen 08", 26, 25) );
    
                cinemaRepository.save(cinema);
    
                returnList.add(cinema);
            }        

        }
        return returnList;
    }

    private void addShowing(String name, String image, List<Cinema> cinemas, LocalDateTime exhibitionStart, LocalDateTime exhibitionEnd, String... screenNames) {
        Movie movie = new Movie(name, sanitize(name), image);
        movieRepository.save(movie);

        for (LocalDateTime date = exhibitionStart; date.isBefore(exhibitionEnd); date = date.plusDays(1)) {

            for ( Cinema cinema : cinemas ) {
                
                for (int time : showingHours) {
                    
                    date = date.withHour(time).truncatedTo(ChronoUnit.HOURS);
                    
                    for ( String screenIdx : screenNames ) {                        
                        //Find the screen By name
                        Screen screen = cinema.getScreens()
                                .stream()
                                .filter( screenFilter -> screenFilter.getName().contains(screenIdx) )
                                .collect(Collectors.toList())
                                .iterator().next();
                        showingRepository.save(new Showing(screen, movie, date));                    
                    }
                }
            }
        }

    }

}


