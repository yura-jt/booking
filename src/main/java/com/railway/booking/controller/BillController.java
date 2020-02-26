package com.railway.booking.controller;

import com.railway.booking.entity.Bill;
import com.railway.booking.entity.Pager;
import com.railway.booking.service.BillService;
import com.railway.booking.service.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class BillController {
    private final BillService billService;

    @GetMapping(value = "/user/bill")
    public ModelAndView bills(@RequestParam(name = "page", required = false) String page) {
        ModelAndView modelAndView = new ModelAndView("user/bill");

        Page<Bill> bills = billService.findAll(page);

        Pager pager = new Pager(bills.getTotalPages(), bills.getNumber());

        modelAndView.addObject("bills", bills);
        modelAndView.addObject("selectedPageSize", Constants.ITEM_PER_PAGE);
        modelAndView.addObject("pageSizes", Constants.ITEM_PER_PAGE);
        modelAndView.addObject("pager", pager);

        return modelAndView;
    }
}