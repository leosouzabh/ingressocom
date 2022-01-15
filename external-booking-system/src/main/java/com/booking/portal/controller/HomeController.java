package com.booking.portal.controller;

import com.booking.portal.pojo.BookingForm;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {

    @PostMapping("/booking")
    BookingForm booking(@RequestBody BookingForm bookingForm) throws InterruptedException {
        bookingForm.setHash("hash");
        Thread.sleep(bookingForm.getDelay() * 1000);
        return bookingForm;
    }

    @GetMapping({"/", ""})
    String index() {
        return "hi";
    }

}


