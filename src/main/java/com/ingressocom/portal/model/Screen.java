package com.ingressocom.portal.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Screen {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Cinema cinema;
    
    private String name;
    private Integer totalRow;
    private Integer totalCol;

    public Screen() {}
    public Screen(Cinema cinema, String name, Integer totalRow, Integer totalCol) {
        super();
        this.cinema = cinema;
        this.name = name;
        this.totalRow = totalRow;
        this.totalCol = totalCol;
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
    public Integer getTotalRow() {
        return totalRow;
    }
    public void setTotalRow(Integer totalRow) {
        this.totalRow = totalRow;
    }
    public Integer getTotalCol() {
        return totalCol;
    }
    public void setTotalCol(Integer totalCol) {
        this.totalCol = totalCol;
    }
    public Cinema getCinema() {
        return cinema;
    }
    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }
    @Override
    public String toString() {
        return "SCREEN=["+this.getName()+"]";
    }
}
