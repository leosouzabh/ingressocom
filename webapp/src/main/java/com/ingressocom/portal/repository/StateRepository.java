package com.ingressocom.portal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.ingressocom.portal.model.State;

@Repository
public interface StateRepository extends CrudRepository<State, Long> {
 
    
}