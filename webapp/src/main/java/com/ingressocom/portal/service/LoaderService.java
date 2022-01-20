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
import com.ingressocom.portal.pojo.MovieLoader;
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
    @Autowired LoaderDataComponent loaderData;

    Logger logger = LoggerFactory.getLogger(LoaderService.class);

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

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for( MovieLoader movieLoader : loaderData.getMovies() ){
            CompletableFuture<Void> future = loaderWorker.addShowing(movieLoader.getMovie(), movieLoader.getImage(), 
                cinemas, exhibitionStart, exhibitionEnd, movieLoader.getScreens());
            futures.add(future);
        }

        CompletableFuture<Void>[] futureArray =  futures.stream().toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futureArray).join();
    }

    

    private Map<String, State> saveStates() {
        Map<String, State> mapReturn = new HashMap<String, State>();
        loaderData.getStates().stream()
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
        for ( String[] stateStr : loaderData.getStates() ) {
            
            for (String cinemaName : loaderData.getCinemas()) {
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
