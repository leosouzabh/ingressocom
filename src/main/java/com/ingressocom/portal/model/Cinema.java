package com.ingressocom.portal.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Cinema {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    
    @OneToMany(mappedBy="cinema", cascade = CascadeType.PERSIST)
    private Set<Screen> screens;
       

    public Set<Screen> getScreens() {
        return screens;
    }
    public void setScreens(Set<Screen> screens) {
        this.screens = screens;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "CINEMA=["+this.getName()+"]";
    }

    
    
}
