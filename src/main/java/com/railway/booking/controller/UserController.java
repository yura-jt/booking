package com.railway.booking.controller;

import com.railway.booking.entity.User;
import com.railway.booking.model.UserDto;
import com.railway.booking.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Log4j2
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = {"/", "/login"})
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/login");
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new ModelAndView("login");
    }

    @GetMapping("/registration")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "user/registration";
    }

    @GetMapping(value = {"/profile"})
    public String profile() {
        return "user/profile";
    }

    @PostMapping(value = {"/profile"})
    public String performLogin() {
        return "user/profile";
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute("registerUser") @Valid UserDto userDto,
                           Errors errors, Model model) {
        if (!errors.hasErrors()) {
            try {
                userService.register(userDto);
                return "profile";
            } catch (Exception e) {
                log.warn(String.format("Can not register user with provided credentials for user with e-mail: %s",
                        userDto.getEmail()), e);
            }
        }
        return "user/registration";
    }
}
