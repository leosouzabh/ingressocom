package com.ingressocom.portal.pojo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DayWeek {

    private LocalDate date;
    private DateTimeFormatter dayFormat;
    private DateTimeFormatter dateFormat;
    private DateTimeFormatter dateFormatUrl;
    private boolean active;
    
    public DayWeek(LocalDate date, LocalDate activeDate) {
        super();
        this.date = date;
        this.dayFormat = DateTimeFormatter.ofPattern("EEE");
        this.dateFormat = DateTimeFormatter.ofPattern("dd/MMM");
        this.dateFormatUrl = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        this.active = getDateUrl().equals(dateFormatUrl.format(activeDate));
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public String getDayOfWeek() {
        return dayFormat.format(date);
    }
    
    public String getDayMonth() {
        return dateFormat.format(date);
    }
    public String getDateUrl() {
        return dateFormatUrl.format(date);
    }

    public boolean isActive() {
        return active;
    }
    
    
    
}
