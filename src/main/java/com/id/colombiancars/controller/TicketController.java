package com.id.colombiancars.controller;

import com.id.colombiancars.entity.Ticket;
import com.id.colombiancars.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@CrossOrigin(origins = "*")
public class TicketController {

    @Autowired
    private TicketService ticketService;


    // Find All
    @GetMapping(value = "/findAllTickets")
    public List<Ticket> findAllBooks(){
        return ticketService.findAllTickets();
    }


}
