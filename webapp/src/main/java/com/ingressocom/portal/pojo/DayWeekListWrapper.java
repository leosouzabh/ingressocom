package com.ingressocom.portal.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DayWeekListWrapper {

    private List<DayWeek> list;
    private String activeDayStr;

    public DayWeekListWrapper(String activeDayStr) {
        this.activeDayStr = activeDayStr;
        list = new ArrayList<>();
    }

    public List<DayWeek> getList() {
        return list;
    }

    public void addDate(Date date) {
        list.add(new DayWeek(date, activeDayStr));
    }
    
    
}
