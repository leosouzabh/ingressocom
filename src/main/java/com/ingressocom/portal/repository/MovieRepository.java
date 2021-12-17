package com.ingressocom.portal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.ingressocom.portal.model.Movie;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
    
}