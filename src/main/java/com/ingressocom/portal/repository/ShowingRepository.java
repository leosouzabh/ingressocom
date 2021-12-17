package com.ingressocom.portal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.ingressocom.portal.model.Showing;

@Repository
public interface ShowingRepository extends CrudRepository<Showing, Long> {
    
}