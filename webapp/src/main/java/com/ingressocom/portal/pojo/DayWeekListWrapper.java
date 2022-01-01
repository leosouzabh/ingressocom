package com.ingressocom.portal.pojo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DayWeekListWrapper {

    private List<DayWeek> list;
    private LocalDate activeDay;

    public DayWeekListWrapper(LocalDate activeDay) {
        this.activeDay = activeDay;
        list = new ArrayList<>();
    }

    public List<DayWeek> getList() {
        return list;
    }

    public void addDate(LocalDate date) {
        list.add(new DayWeek(date, activeDay));
    }
    
    public DayWeek getActiveDate() {
        return this.list.stream().filter( date -> date.isActive() ).findFirst().get();
    }
    
}
