package com.ingressocom.portal.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ingressocom.portal.model.Movie;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {

    @Query("from Movie m where m.code = :movieCode")
    Optional<Movie> findByCode(@Param("movieCode") String movieCode);    

    @Query("SELECT COUNT(m) FROM Movie m")
    long findTotalMovie();
}