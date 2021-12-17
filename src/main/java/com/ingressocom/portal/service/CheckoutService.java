package com.ingressocom.portal.service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import org.springframework.stereotype.Component;
import com.ingressocom.portal.pojo.DayWeekListWrapper;

@Component
public class CheckoutService {

    public DayWeekListWrapper getListDates(String activeDay) {
        
        DayWeekListWrapper listReturn = new DayWeekListWrapper(activeDay);
        
        Calendar now = new GregorianCalendar();
        listReturn.addDate(now.getTime());

        for ( int x=1; x<=7; x++ ) {
            now.add(Calendar.DAY_OF_YEAR, 1);
            listReturn.addDate(now.getTime());
        }
        
        return listReturn;
    }

}
