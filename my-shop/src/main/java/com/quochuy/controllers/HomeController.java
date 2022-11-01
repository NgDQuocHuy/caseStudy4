package com.quochuy.controllers;

import com.quochuy.models.User;
import com.quochuy.services.user.IUserService;
import com.quochuy.utils.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private AppUtil appUtil;

    @Autowired
    private IUserService userService;

    @GetMapping
    public ModelAndView showHomePage() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("product/list");
        String username = appUtil.getPrincipalUsername();
        modelAndView.addObject("username", username);
        return modelAndView;
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ModelAndView showUserPage() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/listUser");
        String username = appUtil.getPrincipalUsername();
        modelAndView.addObject("username", username);
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView showLoginPage() {
        String username = appUtil.getPrincipalUsername();
        System.out.println(username);
        if (username.equals("anonymousUser")) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("login");
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("product/list");
        modelAndView.addObject("username", username);
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView showRegisterPage() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");

        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public String handleError(Exception e) { return "redirect:/";
    }
}
