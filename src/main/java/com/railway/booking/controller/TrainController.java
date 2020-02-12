package com.railway.booking.controller;

import com.railway.booking.model.Pager;
import com.railway.booking.model.Train;
import com.railway.booking.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TrainController {
    private static final int PAGE_SIZES = 5;

    @Autowired
    private TrainService trainService;

    @RequestMapping(value = "/trains", method = RequestMethod.GET)
    public ModelAndView listBooks(
            Model model,
            @RequestParam(name = "page", required = false) String page) {
        ModelAndView modelAndView = new ModelAndView("/train/trains");


        Page<Train> trains = trainService.findAll(page);

        Pager pager = new Pager(trains.getTotalPages(), trains.getNumber());

        modelAndView.addObject("trains", trains);
        modelAndView.addObject("selectedPageSize", PAGE_SIZES);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);

        return modelAndView;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String testPaginations() {
        return "test";
    }

}