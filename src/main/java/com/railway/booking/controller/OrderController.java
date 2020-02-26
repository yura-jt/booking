package com.railway.booking.controller;

import com.railway.booking.entity.Order;
import com.railway.booking.entity.Pager;
import com.railway.booking.service.OrderService;
import com.railway.booking.service.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping(value = "/user/orders")
    public ModelAndView bills(
            Model model,
            @RequestParam(name = "page", required = false) String page) {
        ModelAndView modelAndView = new ModelAndView("user/orders");

        Page<Order> orders = orderService.findAll(page);

        Pager pager = new Pager(orders.getTotalPages(), orders.getNumber());

        modelAndView.addObject("orders", orders);
        modelAndView.addObject("selectedPageSize", Constants.ITEM_PER_PAGE);
        modelAndView.addObject("pageSizes", Constants.ITEM_PER_PAGE);
        modelAndView.addObject("pager", pager);

        return modelAndView;
    }

    @GetMapping(value = "/user/makeOrder")
    public String makeOrder() {
        return "user/makeOrder";
    }
}