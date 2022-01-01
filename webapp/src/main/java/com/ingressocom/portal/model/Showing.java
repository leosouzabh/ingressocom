package com.ingressocom.portal.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Showing {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "datetime", columnDefinition = "TIMESTAMP")
    private LocalDateTime datetime;
    
    @Column(name = "date", columnDefinition = "DATE")
    private LocalDate date;
    
    @ManyToOne
    private Screen screen;
    
    @ManyToOne
    private Movie movie;
    
    @Column(name = "full_capacity")
    private Boolean full;

    public Showing() {}

    public Showing(Screen screen, Movie movie, LocalDateTime datetime) {
        super();
        this.datetime = datetime;
        this.date     = datetime.toLocalDate();
        this.screen   = screen;
        this.movie    = movie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }
    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
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
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    @Override
    public String toString() {
        return this.getScreen().getCinema() + " - " + this.getScreen() + ": " + this.getMovie();
    }
    
    public Boolean getFull() {
        return full;
    }

    public void setFull(Boolean full) {
        this.full = full;
    }

    public String convertPosition(int row) {
        try {
            String alpha = "ABCDEFGHIJKLMNOPQRSTUVXYWZ";
            return alpha.substring(row-1, row);            
        } catch (Exception e) {
            return "";
        }
    }
    
    
    
    
    
}
