package com.railway.booking.controller;

import com.railway.booking.model.Bill;
import com.railway.booking.model.Pager;
import com.railway.booking.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TicketController {
    private static final int PAGE_SIZES = 5;

    @Autowired
    private BillService billService;

    @GetMapping(value = {"/tickets"})
    public String tickets() {
        return "ticket/tickets";
    }

    @RequestMapping(value = "/bills", method = RequestMethod.GET)
    public ModelAndView bills(
            Model model,
            @RequestParam(name = "page", required = false) String page) {
        ModelAndView modelAndView = new ModelAndView("bills");

        Page<Bill> bills = billService.findAll(page);

        Pager pager = new Pager(bills.getTotalPages(), bills.getNumber());

        modelAndView.addObject("bills", bills);
        modelAndView.addObject("selectedPageSize", PAGE_SIZES);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);

        return modelAndView;
    }
}