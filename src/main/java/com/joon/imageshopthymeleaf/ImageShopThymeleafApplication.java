package com.joon.imageshopthymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class ImageShopThymeleafApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageShopThymeleafApplication.class, args);
    }

}
