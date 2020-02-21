package com.railway.booking.controller;

import com.railway.booking.entity.User;
import com.railway.booking.domain.UserEntity;
import com.railway.booking.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController implements WebMvcConfigurer {

    private final UserService userService;

    @GetMapping(value = {"/"})
    public ModelAndView loginPage(@RequestParam(value = "error", required = false) String error) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("loginError", "Invalid Credentials provided.");
        }

        model.setViewName("login");
        return model;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new ModelAndView("/");
    }

    @GetMapping(value = {"/user/profile"})
    public String profile() {
        return "user/profile";
    }

    @PostMapping(value = {"/user/profile"})
    public String performLogin() {
        return "redirect:user/profile";
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("/login");
    }

    @GetMapping("/registration")
    public String registerForm(UserEntity userEntity) {
        return "registration";
    }

    @PostMapping("/registration")
    public String register(@Valid UserEntity userEntity, BindingResult bindingResult) {
        Optional<User> user = userService.findByEmail(userEntity.getEmail());
        if (!user.isPresent()) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        String password = userEntity.getPassword();
        if (password != null && password.equals(userEntity.getRepeatedPassword())) {
            bindingResult
                    .rejectValue("password", "error.user",
                            "Repeated password doesn't match");
        }
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.register(userEntity);
        return "redirect:/login";
    }
}
