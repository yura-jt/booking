package com.railway.booking.service;

import com.railway.booking.entity.Ticket;
import org.springframework.data.domain.Page;

public interface TicketService {

    Page<Ticket> findAll(String pageNumber);
}
