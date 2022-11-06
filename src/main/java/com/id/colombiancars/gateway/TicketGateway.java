package com.id.colombiancars.gateway;

import com.id.colombiancars.entity.Ticket;

import java.util.List;

public interface TicketGateway {

    List<Ticket> findAllTickets();
}
