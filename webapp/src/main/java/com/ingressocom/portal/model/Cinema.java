package com.ingressocom.portal.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Cinema {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String code;
    
    @OneToMany(mappedBy="cinema", cascade = CascadeType.PERSIST)
    private Set<Screen> screens;

    private String city;
    
    @ManyToOne
    private State state;
    
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
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    @Override
    public String toString() {
        return "CINEMA=["+this.getName()+"]";
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public State getState() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
    }

    public String getAddress() {
        return getCity() + ", " + getState().getName() + "  ("+getState().getAbbreviation()+")";
    }
    
    
}
