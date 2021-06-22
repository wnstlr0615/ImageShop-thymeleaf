package com.joon.imageshopthymeleaf.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home( Model model){
        LocalDateTime now=LocalDateTime.now();
        String serverTime = now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        model.addAttribute("serverTime", serverTime);
        return "home";
    }
}
