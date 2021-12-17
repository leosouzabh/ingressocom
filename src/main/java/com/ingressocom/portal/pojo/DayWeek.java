package com.ingressocom.portal.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DayWeek {

    private Date date;
    private SimpleDateFormat dayFormat;
    private SimpleDateFormat dateFormat;
    private boolean active;
    
    public DayWeek(Date date, String activeDateStr) {
        super();
        this.date = date;
        this.dayFormat = new SimpleDateFormat("EEE");
        this.dateFormat = new SimpleDateFormat("dd/MM");
        
        activeDateStr = activeDateStr == null ? 
                dateFormat.format(new Date()) : activeDateStr;
                
        this.active = getDayMonth().equals(activeDateStr);
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public String getDayOfWeek() {
        return dayFormat.format(date);
    }
    
    public String getDayMonth() {
        return dateFormat.format(date);
    }

    public boolean isActive() {
        return active;
    }
    
    
    
}
