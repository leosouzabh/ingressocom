package com.ingressocom.portal.pojo;

import java.util.Arrays;
import java.util.List;

public class MovieLoader {
    private String movie;
    private String image;
    private List<String> screens;
    
    public MovieLoader(String movie, String image, String... screens){
        this.screens = Arrays.asList(screens);
        this.movie = movie;
        this.image = image;
    }
    

    public String getMovie() {
        return movie;
    }
    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getScreens() {
        return screens;
    }
    public void setScreens(List<String> screens) {
        this.screens = screens;
    }
}
