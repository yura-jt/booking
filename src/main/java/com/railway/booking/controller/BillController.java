package com.railway.booking.controller;

import com.railway.booking.entity.Bill;
import com.railway.booking.entity.Pager;
import com.railway.booking.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BillController {
    private static final int PAGE_SIZES = 5;

    @Autowired
    private BillService billService;

    @GetMapping(value = "/user/bill")
    public ModelAndView bills(@RequestParam(name = "page", required = false) String page) {
        ModelAndView modelAndView = new ModelAndView("user/bill");

        Page<Bill> bills = billService.findAll(page);

        Pager pager = new Pager(bills.getTotalPages(), bills.getNumber());

        modelAndView.addObject("bills", bills);
        modelAndView.addObject("selectedPageSize", PAGE_SIZES);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);

        return modelAndView;
    }
}