package com.booking.portal.controller;

import com.booking.portal.pojo.BookingForm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {

    @Value("${SLEEPTIME_SECONDS}")
    String sleepTimeSeconds;

    @PostMapping("/booking")
    BookingForm booking(@RequestBody BookingForm bookingForm) throws InterruptedException {
        bookingForm.setHash("hash");
        Thread.sleep(Integer.parseInt(sleepTimeSeconds) * 1000);
        return bookingForm;
    }

}


