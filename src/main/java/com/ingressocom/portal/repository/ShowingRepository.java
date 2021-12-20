package com.ingressocom.portal.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ingressocom.portal.model.Movie;
import com.ingressocom.portal.model.Showing;

@Repository
public interface ShowingRepository extends CrudRepository<Showing, Long> {
 
    @Query("from Showing s join fetch s.screen where s.movie = :movie")
    List<Showing> findByMovie(@Param("movie") Movie movie);
}