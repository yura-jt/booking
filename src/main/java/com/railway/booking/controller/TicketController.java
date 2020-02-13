package com.railway.booking.controller;

import com.railway.booking.entity.Pager;
import com.railway.booking.entity.Ticket;
import com.railway.booking.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TicketController {
    private static final int PAGE_SIZES = 5;

    @Autowired
    private TicketService ticketService;

    @GetMapping(value = "/tickets")
    public ModelAndView bills(@RequestParam(name = "page", required = false) String page) {
        ModelAndView modelAndView = new ModelAndView("ticket/tickets");

        Page<Ticket> tickets = ticketService.findAll(page);

        Pager pager = new Pager(tickets.getTotalPages(), tickets.getNumber());

        modelAndView.addObject("tickets", tickets);
        modelAndView.addObject("selectedPageSize", PAGE_SIZES);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);

        return modelAndView;
    }
}