package com.ingressocom.portal.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Showing {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String time;
    
    @ManyToOne
    private Screen screen;
    
    @ManyToOne
    private Movie movie;

    public Showing() {
        
    }
    public Showing(Screen screen, Movie movie, String time) {
        super();
        this.time = time;
        this.screen = screen;
        this.movie = movie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }
    public Movie getMovie() {
        return movie;
    }
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    @Override
    public String toString() {
        return this.getScreen().getCinema() + " - " + this.getScreen() + ": " + this.getMovie();
    }
    public String convertPosition(int row) {
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVXYWZ";
        return alpha.substring(row-1, row);
    }
    
    
    
    
    
}
