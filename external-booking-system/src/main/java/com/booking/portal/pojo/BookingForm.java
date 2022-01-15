package com.booking.portal.pojo;

public class BookingForm {

    private String seat;
    private String hash;
    private Integer delay;
    
    public String getHash() {
        return hash;
    }
    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSeat() {
        return seat;
    }
    public void setSeat(String seat) {
        this.seat = seat;
    }

    public Integer getDelay() {
        return delay == null ? 3 : delay;
    }
    public void setDelay(Integer delay) {
        this.delay = delay;
    }

}
