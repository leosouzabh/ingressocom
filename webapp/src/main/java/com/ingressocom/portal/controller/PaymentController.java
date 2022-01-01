package com.ingressocom.portal.controller;

import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.ingressocom.portal.exception.SeatAlreadyLocked;
import com.ingressocom.portal.model.Showing;
import com.ingressocom.portal.pojo.PaymentForm;
import com.ingressocom.portal.service.BookingService;
import com.ingressocom.portal.service.CheckoutService;
import com.ingressocom.portal.service.PaymentService;
import com.ingressocom.portal.service.ShowingService;


@Controller
@RequestMapping({"/payment"})
public class PaymentController extends BaseController {
    
    @Autowired BookingService bookingService;
    @Autowired ShowingService showingService;
    @Autowired CheckoutService checkoutService;
    @Autowired PaymentService paymentService;
    
    @GetMapping({"/init"})
    public String init (
            @RequestParam(value = "showing", required = false) Long id,
            @RequestParam(value = "seat", required = false) String seat,
            Model model,
            RedirectAttributes redirAttrs) {
        
        Showing showing = showingService.findById(id);
        String httpSessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        try {
            if ( !seat.equals(getSeatFromSession()) ) { //F5
                bookingService.lockSeat(showing, seat, httpSessionId);
                super.setSeatToSession(seat);
            } 
            
            model.addAttribute("showing", showing);
            
            PaymentForm form = new PaymentForm();
            form.setShowingId(showing.getId());
            form.setSeat(seat);
            model.addAttribute("paymentForm", form);
            return "payment/card-form";
            
        } catch (SeatAlreadyLocked e) {
            redirAttrs.addFlashAttribute("error", "Seat already selected.");            
            String movie = showing.getMovie().getCode();
            String date = showing.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return "redirect:/movie/"+movie+"/"+date+"/seat?showing="+showing.getId();
        }
        
    }
    
    
    @PostMapping({"/process"})
    public String process(
            @ModelAttribute(value="paymentForm") PaymentForm paymentForm,
            Model model,
            RedirectAttributes redirAttrs) {
        
        Showing showing = showingService.findById(paymentForm.getShowingId());
        paymentService.processPayment(showing, paymentForm);
        showingService.checkAvailability(showing);
        super.invalidateSession();
        return "payment/sucess";
        
    }
    
}
