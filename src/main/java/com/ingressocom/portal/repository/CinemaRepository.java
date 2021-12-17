package com.ingressocom.portal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.ingressocom.portal.model.Cinema;

@Repository
public interface CinemaRepository extends CrudRepository<Cinema, Long> {
    
}