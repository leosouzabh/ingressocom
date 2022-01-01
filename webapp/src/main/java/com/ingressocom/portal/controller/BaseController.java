package com.ingressocom.portal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.ingressocom.portal.model.State;

public class BaseController {

    private static final String STATE_SESSION = "STATE_SESSION";
    private static final String SEAT_SESSION = "SEAT_SESSION";
    
    public State getStateFromSession() {
        return (State) getHttpSession().getAttribute(STATE_SESSION);
    }
    
    public void setStateToSession(State state) {
        getHttpSession().setAttribute(STATE_SESSION, state);
    }
    
    public void setSeatToSession(String seat) {
        getHttpSession().setAttribute(SEAT_SESSION, seat);
    }
    
    public String getSeatFromSession() {
        return (String) getHttpSession().getAttribute(SEAT_SESSION);
    }

    public void invalidateSession() {
        getHttpSession().invalidate();
    }
    
    private HttpSession getHttpSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession(true);//true will create if necessary
        return session;
    }
    
    
    
}
