package com.ingressocom.portal.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.ingressocom.portal.model.Cinema;
import com.ingressocom.portal.model.Screen;
import com.ingressocom.portal.model.State;
import com.ingressocom.portal.repository.CinemaRepository;
import com.ingressocom.portal.repository.MovieRepository;
import com.ingressocom.portal.repository.ShowingRepository;
import com.ingressocom.portal.repository.StateRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoaderService {

    @Autowired MovieRepository movieRepository;
    @Autowired CinemaRepository cinemaRepository;
    @Autowired ShowingRepository showingRepository;
    @Autowired StateRepository stateRepository;
    @Autowired LoaderWorker loaderWorker;
    
    Logger logger = LoggerFactory.getLogger(LoaderService.class);

    
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
        
    
    public boolean loadData() {
        boolean dataLoaded = false;
        if ( ! isDataLoaded() ) {
            try {
                doLoad();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            dataLoaded = true;
        } 
        return dataLoaded;
    }

    public boolean isDataLoaded(){
        return movieRepository.findTotalMovie() > 0;
    }

    public void doLoad() throws InterruptedException {

        Map<String, State> usStates = saveStates();
        
        logger.info("Loader step: Adding cinemas");
        List<Cinema> cinemas = saveCinema(usStates);
        
        LocalDateTime exhibitionStart = LocalDateTime.now();
        LocalDateTime exhibitionEnd = LocalDateTime.now().plusDays(15);

        CompletableFuture<Void> moviaA = loaderWorker.addShowing("Clifford the Big Red Dog", "a.jpg", cinemas, exhibitionStart, exhibitionEnd, "01");
        CompletableFuture<Void> moviaB = loaderWorker.addShowing("Spiderman: No Way Home", "b.jpg", cinemas, exhibitionStart, exhibitionEnd, "02", "03", "04", "05");
        CompletableFuture<Void> moviaC = loaderWorker.addShowing("Resident Evil: Welcome to Raccoon City", "c.jpg", cinemas, exhibitionStart, exhibitionEnd, "06");
        CompletableFuture<Void> moviaD = loaderWorker.addShowing("Ethernal", "d.jpg", cinemas, exhibitionStart, exhibitionEnd, "07");            
        CompletableFuture<Void> moviaE = loaderWorker.addShowing("Matrix", "k.jpg", cinemas, exhibitionStart, exhibitionEnd, "08");

        CompletableFuture.allOf(moviaA, moviaB, moviaC, moviaD, moviaE).join();
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

    private String sanitize(String in) {
        return in.toLowerCase().replaceAll("-", "").replaceAll(":", "").replaceAll("[^a-zA-Z0-9]", "-");
    }

    

}
