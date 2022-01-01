package com.ingressocom.portal.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ingressocom.portal.model.Movie;
import com.ingressocom.portal.model.Showing;
import com.ingressocom.portal.model.State;

@Repository
public interface ShowingRepository extends CrudRepository<Showing, Long> {
 
    @Query("from Showing s join fetch s.screen screen join fetch screen.cinema cinema where s.movie = :movie and s.date = :date and cinema.state = :state")
    List<Showing> findByMovieAndDate(
                @Param("movie") Movie movie, 
                @Param("date") LocalDate date, 
                @Param("state") State state);
}