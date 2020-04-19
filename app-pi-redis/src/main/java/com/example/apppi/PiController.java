package com.example.apppi;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PiController {
    private PiCalculator calculator;

    public PiController(PiCalculator calculator)
    {
        this.calculator = calculator;
    }

    @GetMapping("/login")
    ResponseEntity<Boolean> login(HttpSession session) 
    {
        System.out.println("login "+session.getId());
        session.setAttribute("login", Boolean.TRUE);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @GetMapping("/pi")
    public ResponseEntity<Long> pi(HttpSession session)
    {
        if (session.getAttribute("login") == null || !(boolean)(session.getAttribute("login"))) 
        {
            return new ResponseEntity<Long>(-1L, HttpStatus.UNAUTHORIZED);
        }
        System.out.println("pi "+session.getId());
        long startTime = System.currentTimeMillis();
        this.calculator.calculatePi(10_000_000);
        long endTime = System.currentTimeMillis();
        return ResponseEntity.ok(Long.valueOf(endTime-startTime));
    }

}