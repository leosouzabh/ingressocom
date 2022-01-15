package com.ingressocom.portal.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.ingressocom.portal.model.Cinema;
import com.ingressocom.portal.model.Movie;
import com.ingressocom.portal.model.Screen;
import com.ingressocom.portal.model.Showing;
import com.ingressocom.portal.repository.MovieRepository;
import com.ingressocom.portal.repository.ShowingRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class LoaderWorker {

    @Autowired MovieRepository movieRepository;
    @Autowired ShowingRepository showingRepository;
    Logger logger = LoggerFactory.getLogger(LoaderService.class);
    
    private int[] showingHours = new int[]{12, 15, 18, 21};

    @Async
    public CompletableFuture<Void> addShowing(
            String name, String image, List<Cinema> cinemas,
            LocalDateTime exhibitionStart, LocalDateTime exhibitionEnd, String... screenNames)
            throws InterruptedException {

        logger.info("Loader step: Adding movie: " + name);
        Movie movie = new Movie(name, sanitize(name), image);
        movieRepository.save(movie);

        for (LocalDateTime date = exhibitionStart; date.isBefore(exhibitionEnd); date = date.plusDays(1)) {

            for (Cinema cinema : cinemas) {
                logger.info("Loader step: Cinema: " + cinema.getName());
                for (int time : showingHours) {

                    date = date.withHour(time).truncatedTo(ChronoUnit.HOURS);

                    for (String screenIdx : screenNames) {
                        // Find the screen By name
                        Screen screen = cinema.getScreens()
                                .stream()
                                .filter(screenFilter -> screenFilter.getName().contains(screenIdx))
                                .collect(Collectors.toList())
                                .iterator().next();
                        showingRepository.save(new Showing(screen, movie, date));
                    }
                }
            }
        }

        return CompletableFuture.completedFuture(null);
    }

    private String sanitize(String in) {
        return in.toLowerCase().replaceAll("-", "").replaceAll(":", "").replaceAll("[^a-zA-Z0-9]", "-");
    }
}
