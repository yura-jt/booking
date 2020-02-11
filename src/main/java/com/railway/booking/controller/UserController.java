package com.railway.booking.controller;

import com.railway.booking.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping(value = {"/", "/login"})
    public String login() {
        return "user/login";
    }

    @GetMapping(value = {"/error"})
    public String error() {
        return "error";
    }

    @GetMapping(value = {"/registration"})
    public String registration(Model model) {
        model.addAttribute("registration", new User());
        return "user/registrationForm";
    }

    @GetMapping(value = {"/profile"})
    public String profile() {
        return "user/profile";
    }

}
