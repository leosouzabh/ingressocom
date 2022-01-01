package com.ingressocom.portal.pojo;

import java.time.LocalDateTime;

public class ExternalBookingInput {

    private String cinema;
    private String screen;
    private String movie;
    private String httpSessionId;
    private LocalDateTime datetime;
    
    public String getCinema() {
        return cinema;
    }
    public void setCinema(String cinema) {
        this.cinema = cinema;
    }
    public String getScreen() {
        return screen;
    }
    public void setScreen(String screen) {
        this.screen = screen;
    }
    public String getMovie() {
        return movie;
    }
    public void setMovie(String movie) {
        this.movie = movie;
    }
    public LocalDateTime getDatetime() {
        return datetime;
    }
    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }
    public String getHttpSessionId() {
        return httpSessionId;
    }
    public void setHttpSessionId(String httpSessionId) {
        this.httpSessionId = httpSessionId;
    }
    
    
    
}
