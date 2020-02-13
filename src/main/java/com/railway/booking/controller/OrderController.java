package com.railway.booking.controller;

import com.railway.booking.entity.Order;
import com.railway.booking.entity.Pager;
import com.railway.booking.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OrderController {
    private static final int PAGE_SIZES = 5;

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/orders")
    public ModelAndView bills(
            Model model,
            @RequestParam(name = "page", required = false) String page) {
        ModelAndView modelAndView = new ModelAndView("ticket/orders");

        Page<Order> orders = orderService.findAll(page);

        Pager pager = new Pager(orders.getTotalPages(), orders.getNumber());

        modelAndView.addObject("orders", orders);
        modelAndView.addObject("selectedPageSize", PAGE_SIZES);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);

        return modelAndView;
    }
}