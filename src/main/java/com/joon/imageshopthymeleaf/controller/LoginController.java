package com.joon.imageshopthymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @GetMapping("/auth/login")
    public String loginForm(String error, String logout, Model model){
        if(error!=null){
            model.addAttribute("error", "Login Error!!!");
        }
        if(logout!=null){
            model.addAttribute("logout", "Logout!!!!");
        }
        return "/auth/loginForm";
    }

}
