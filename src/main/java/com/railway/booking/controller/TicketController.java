package com.railway.booking.controller;

import com.railway.booking.entity.Pager;
import com.railway.booking.entity.Ticket;
import com.railway.booking.service.TicketService;
import com.railway.booking.service.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping(value = "/user/tickets")
    public ModelAndView tickets(@RequestParam(name = "page", required = false) String page) {
        ModelAndView modelAndView = new ModelAndView("user/tickets");

        Page<Ticket> tickets = ticketService.findAll(page);
        Pager pager = new Pager(tickets.getTotalPages(), tickets.getNumber());

        modelAndView.addObject("tickets", tickets);
        modelAndView.addObject("selectedPageSize", Constants.ITEM_PER_PAGE);
        modelAndView.addObject("pageSizes", Constants.ITEM_PER_PAGE);
        modelAndView.addObject("pager", pager);

        return modelAndView;
    }
}