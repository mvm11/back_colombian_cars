package com.id.colombiancars.service;

import com.id.colombiancars.entity.Ticket;
import com.id.colombiancars.gateway.TicketGateway;
import com.id.colombiancars.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService implements TicketGateway {

    @Autowired
    TicketRepository ticketRepository;

    @Override
    public List<Ticket> findAllTickets() {
        return ticketRepository.findAll();
    }
}
