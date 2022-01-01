package com.ingressocom.portal.pojo;

public class PaymentForm {

    private Long showingId;
    private String seat;
    
    private String cardNumber;
    private String cardOwner;
    private String expireMonth;
    private String expireYear;
    private String cvv;
    
    
    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public String getCardOwner() {
        return cardOwner;
    }
    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner;
    }
    public String getExpireMonth() {
        return expireMonth;
    }
    public void setExpireMonth(String expireMonth) {
        this.expireMonth = expireMonth;
    }
    public String getExpireYear() {
        return expireYear;
    }
    public void setExpireYear(String expireYear) {
        this.expireYear = expireYear;
    }
    public String getCvv() {
        return cvv;
    }
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    public Long getShowingId() {
        return showingId;
    }
    public void setShowingId(Long showingId) {
        this.showingId = showingId;
    }
    public String getSeat() {
        return seat;
    }
    public void setSeat(String seat) {
        this.seat = seat;
    }
    
    
    
}
