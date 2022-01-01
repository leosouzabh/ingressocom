package com.ingressocom.portal.service;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.ingressocom.portal.model.Showing;
import com.ingressocom.portal.model.Ticket;
import com.ingressocom.portal.pojo.PaymentForm;
import com.ingressocom.portal.pojo.PaymentGatewayInput;
import com.ingressocom.portal.pojo.PaymentGatewayOutput;

@Component
public class PaymentService {

    @Autowired TicketService ticketService;
    @Autowired BookingService bookingService;
    
    @Value("${PAYMENT_GATEWAY_ENDPOINT}")
    String paymentGatewayEndpoint;
    
    @Autowired RestTemplate restTemplate;
    
    public void processPayment(Showing showing, PaymentForm paymentForm) {
        PaymentGatewayOutput output = callPaymentGateway(paymentForm);
        createTicket(showing, output, paymentForm.getSeat());
    }

    private void createTicket(Showing showing, PaymentGatewayOutput output, String seat) {
        Ticket ticket = new Ticket();
        ticket.setDatetime(LocalDateTime.now());
        ticket.setPaymentHash(output.getHash());
        ticket.setShowing(showing);
        ticket.setSeat(seat);
        ticketService.save(ticket);
    }

    private PaymentGatewayOutput callPaymentGateway(PaymentForm paymentForm) {
        PaymentGatewayInput input = new PaymentGatewayInput();
        input.setCardNumber(paymentForm.getCardNumber());
        input.setCardOwner(paymentForm.getCardOwner());
        input.setExpireMonth(paymentForm.getExpireMonth());
        input.setExpireYear(paymentForm.getExpireYear());
        input.setCvv(paymentForm.getCvv());

        ResponseEntity<PaymentGatewayOutput> output = restTemplate.postForEntity(paymentGatewayEndpoint, input, PaymentGatewayOutput.class);
        return output.getBody();
    }   

}