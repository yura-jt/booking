package com.railway.booking.controller;

import com.railway.booking.entity.Pager;
import com.railway.booking.entity.Train;
import com.railway.booking.service.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class TrainController {
    private static final int PAGE_SIZES = 5;

    private final TrainService trainService;

    @GetMapping(value = "/user/trains")
    public ModelAndView listTrains(@RequestParam(name = "page", required = false) String page) {
        ModelAndView modelAndView = new ModelAndView("/user/trains");

        Page<Train> trains = trainService.findAll(page);

        Pager pager = new Pager(trains.getTotalPages(), trains.getNumber());

        modelAndView.addObject("trains", trains);
        modelAndView.addObject("selectedPageSize", PAGE_SIZES);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);

        return modelAndView;
    }
}