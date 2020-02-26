package com.railway.booking.controller;

import com.railway.booking.entity.Pager;
import com.railway.booking.entity.Train;
import com.railway.booking.service.TrainService;
import com.railway.booking.service.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @code TrainController is a class that manages input requests from view and
 * supply it with pageable resources of {@code Train} representation.
 *
 * <p>Represents controller layer
 *
 * @see org.springframework.web.servlet.mvc.Controller
 */
@Controller
@RequiredArgsConstructor
public class TrainController {

    /** Train service bean that will be injected to controller for maintaining its tasks */
    private final TrainService trainService;

    /**
     * Main controller for representing pageable Train entities to the View layer
     * @param page is request param from view that represented asked page
     * @return {@code ModelAndView} object with provided data
     */
    @GetMapping(value = "/user/trains")
    public ModelAndView listTrains(@RequestParam(name = "page", required = false) String page) {
        ModelAndView modelAndView = new ModelAndView("/user/trains");

        Page<Train> trains = trainService.findAll(page);

        Pager pager = new Pager(trains.getTotalPages(), trains.getNumber());

        modelAndView.addObject("trains", trains);
        modelAndView.addObject("selectedPageSize", Constants.ITEM_PER_PAGE);
        modelAndView.addObject("pageSizes", Constants.ITEM_PER_PAGE);
        modelAndView.addObject("pager", pager);

        return modelAndView;
    }
}