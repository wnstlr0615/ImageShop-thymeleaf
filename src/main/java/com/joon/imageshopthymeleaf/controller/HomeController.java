package com.joon.imageshopthymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home( Model model){
        LocalDateTime now=LocalDateTime.now();
        model.addAttribute("serverTime", now);
        return "home";
    }
}
